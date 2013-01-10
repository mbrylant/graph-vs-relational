package org.mbrylant.tests.graphs.api.model

/**
 * Created with IntelliJ IDEA.
 * User: mariuszb
 * Date: 10/01/2013
 * Time: 07:31
 * To change this template use File | Settings | File Templates.
 */
abstract class AbstractNode implements INode {

    String prettyPrint() {
        (1..this.nodeType.indent).collect { "\t" }.tail().join("").concat("-").concat(this.nodeType.toString()).concat("\n").concat(children.collect { AbstractNode child -> child.prettyPrint() }.join("\n"))
    }
}
