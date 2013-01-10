package org.mbrylant.tests.graphs.relationaldb.services

import org.mbrylant.tests.graphs.api.model.INode

interface INodeService {
    INode create(final INode node)
    INode load(final Long id)
}
