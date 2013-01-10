package org.mbrylant.tests.graphs.graphdb.model

import org.mbrylant.tests.graphs.api.model.AbstractNode
import org.mbrylant.tests.graphs.api.model.INode
import org.mbrylant.tests.graphs.api.model.NodeType

import javax.persistence.*

class Node extends AbstractNode {

    @Id private String id
    @Version private Long version;
    private NodeType nodeType
    private long level = 0
    private String name = UUID.randomUUID().toString()
    @ManyToOne private INode parent
    @OneToMany private Set<INode>  children

    String getId() { id }
    Long getVersion() { version }

    NodeType getNodeType() { nodeType }
    INode setNodeType(NodeType nodeType) { this.nodeType = nodeType; this }

    long getLevel() { level }
    INode setLevel(long level) { this.level = level; this }

    String getName() { return name }
    INode setName(String name) { this.name = name; this  }

    INode getParent() { parent }
    INode setParent(INode parent) { this.parent = parent; this }

    Set<INode> getChildren() { if (!children) children = new HashSet<Node>(); children }
    Node setChildren(Set<INode> children) {
        this.children = children
        children.each { Node node -> node.setParent(this) }
        this
    }

    void recalculateLevels(long current){
        this.level = current
        long next = ++current
        this.children.each { Node child -> child.recalculateLevels(next) }
    }


}
