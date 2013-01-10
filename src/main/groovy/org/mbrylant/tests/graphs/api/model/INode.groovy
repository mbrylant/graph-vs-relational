package org.mbrylant.tests.graphs.api.model
/**
 * Created with IntelliJ IDEA.
 * User: mariuszb
 * Date: 09/01/2013
 * Time: 21:56
 * To change this template use File | Settings | File Templates.
 */
interface INode {

    NodeType getNodeType()
    INode setNodeType(NodeType nodeType)

//    long getLevel()
//    INode setLevel(long level)

//    String getName()
//    INode setName(String name)

//    INode getParent()
    INode setParent(INode parent)

    Set<INode> getChildren()
    INode setChildren(Set<INode> children)

    void recalculateLevels(long current)
}
