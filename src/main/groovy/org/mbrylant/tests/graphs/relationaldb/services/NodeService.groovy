package org.mbrylant.tests.graphs.relationaldb.services

import org.mbrylant.tests.graphs.api.model.INode
import org.mbrylant.tests.graphs.relationaldb.dao.NodeDAO
import org.mbrylant.tests.graphs.relationaldb.model.Node
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class NodeService implements INodeService {

    @Autowired
    private NodeDAO dao


    public INode create(final INode node) {
        return this.dao.create(node)
    }

    public INode load(final Long id) {
        return dao.load(id)
    }
}
