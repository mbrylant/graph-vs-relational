package org.mbrylant.tests.graphs.utils

import org.apache.commons.lang.reflect.ConstructorUtils
import org.apache.log4j.Logger
import org.mbrylant.tests.graphs.api.model.INode

class Graphs {

    private final static Logger LOG = Logger.getLogger(Graphs)

    static INode generateGraph(INode node, int maxWidth, boolean randomize) {
        def range = (0..maxWidth).collect { it }
        def random = range.getAt(new Random().nextInt(range.size()))
//        LOG.info("Growing graph for node type ${node.nodeType} with max width ${maxWidth} and actual width ${random}")

        def effectiveWidth = randomize ? random: maxWidth
        if (node.nodeType.canContinue()) {
            def children = (1..effectiveWidth).collect {
                def child = (ConstructorUtils.invokeConstructor(node.class) as INode).setNodeType(node.nodeType.transition())
                generateGraph(child, maxWidth, randomize)
            } as Set<INode>
            node.setChildren(children)
            node.recalculateLevels(0)
        }
        node
    }
}
