package org.mbrylant.tests.graphs.relationaldb.dao

import org.mbrylant.tests.graphs.api.model.INode
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import org.mbrylant.tests.graphs.relationaldb.model.Node



@Repository("nodeDao")
class NodeDAO {

    @PersistenceContext
    protected EntityManager em;

    public INode create(final INode node) {
        this.em.persist(node);
        return node;
    }

    public INode load(final Long id) {
        this.em.find(Node.class, id)
    }
}
