package org.mbrylant.tests.graphs.relationaldb.model

import org.hibernate.annotations.GenericGenerator
import org.mbrylant.tests.graphs.api.model.AbstractNode
import org.mbrylant.tests.graphs.api.model.INode
import org.mbrylant.tests.graphs.api.model.NodeType

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.persistence.CascadeType
import javax.persistence.Version

@Entity
@Table(name = "nodes")
class Node extends AbstractNode {

    @Id
//    @GeneratedValue(generator="system-uuid")
//    @GenericGenerator(name="system-uuid", strategy = "guid") @Column(length = 40)
    @GeneratedValue
    private Long id

    INode setId(Long id) { this.id = id; this }
    public Long getId() { return id }

    Long getVersion() { version }
    @Version private Long version;

    NodeType getNodeType() { nodeType }
    INode setNodeType(NodeType nodeType) { this.nodeType = nodeType; this }
    private NodeType nodeType

    long getLevel() { return level }
    INode setLevel(long level) { this.level = level; this }
    private long level = 0

    private String name = UUID.randomUUID().toString()
    String getName() { return name }
    INode setName(String name) { this.name = name; this }

    @OneToMany(mappedBy = "parent", cascade = [ CascadeType.ALL ] )
    private Set<Node>  children

    Set<INode> getChildren() {
        if (!children) children = new HashSet<Node>()
        children
    }
    INode setChildren(Set<INode> children) {
        this.children = children as Set<Node>
        children.each { INode node -> node.setParent(this) }
        this
    }

    INode getParent() { return parent }
    INode setParent(INode parent) { this.parent = (Node)parent; this }
    @ManyToOne
    @JoinTable(name = "nodes_to_nodes", joinColumns = @JoinColumn(name = "parentId"), inverseJoinColumns = @JoinColumn(name = "childId"))
    private Node parent

    void recalculateLevels(long current){
        this.level = current
        long next = current+1
        this.children.each { Node child -> child.recalculateLevels(next) }
    }



}
