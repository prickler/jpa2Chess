package com.capgemini.chess.dataaccess.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.capgemini.chess.dataaccess.dao.PlayerDao;
import com.capgemini.chess.dataaccess.entities.PlayerEntity;
import com.capgemini.chess.queries.QueryNames;
import com.capgemini.chess.service.mapper.Mapper;
import com.capgemini.chess.service.mapper.impl.PlayerMapper;
import com.capgemini.chess.service.to.PlayerTO;


@Repository
public class PlayerDaoImpl extends AbstractDaoImpl<PlayerEntity, PlayerTO, Long> implements PlayerDao {

	@Autowired
	PlayerMapper playerMapper;

	@Override
	public List<PlayerTO> findByLevel(long level) {
		TypedQuery<PlayerEntity> query = entityManager.createNamedQuery(QueryNames.PLAYER_ENTITY_FIND_BY_LVL,
				PlayerEntity.class);
		query.setParameter("level", level);
		return getMapper().mapEs(query.getResultList());
	}

	@Override
	public PlayerTO findByID(long id) {
		TypedQuery<PlayerEntity> query = entityManager.createNamedQuery(QueryNames.PLAYER_ENTITY_FIND_BY_ID,
				PlayerEntity.class);
		query.setParameter("id", id);
		return getMapper().mapE(query.getSingleResult());
	}

	@Override
	protected Mapper<PlayerEntity, PlayerTO> getMapper() {
		return playerMapper;
	}
	    
	@Override
	public List<PlayerTO> findByUserameAndEmail(String username, String email) {
	    	
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
	    CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<PlayerEntity> query = criteriaBuilder.createQuery(PlayerEntity.class);
		Root<PlayerEntity> root = query.from(PlayerEntity.class);
	   
	    List<Predicate> predicates = new ArrayList<Predicate>();
	    if (username != null) {
	        predicates.add(
	                qb.equal(root.get("username"), username));
	    }
	    if (email != null) {
	        predicates.add(
	                qb.equal(root.get("email"), email));
	    }
	    //query itself
	    query.select(root)
	            .where(predicates.toArray(new Predicate[]{}));
	    //execute query and return
	    return getMapper().mapEs(entityManager.createQuery(query).getResultList());
	}
}
