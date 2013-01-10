package org.mbrylant.tests.graphs.api.model

/**
 * Created with IntelliJ IDEA.
 * User: mariuszb
 * Date: 05/01/2013
 * Time: 15:32
 * To change this template use File | Settings | File Templates.
 */
enum NodeType {
    DOCUMENT(false, 1), CHAPTER(false, 2), PARAGRAPH(false, 3), LINE(false, 4), WORD(false, 5), LETTER(true, 6);

    private boolean terminating

    int getIndent() { indent }

    private int indent = 0

//    NodeType(boolean terminating){ this.terminating  = terminating }

    NodeType(boolean terminating, int indent){ this.terminating  = terminating; this.indent = indent }

    public boolean isTerminating() { terminating }

    public boolean canContinue() { ! terminating }

    static transitions = [:] as Map<NodeType, NodeType>

    static {
        transitions.put(DOCUMENT, CHAPTER)
        transitions.put(CHAPTER, PARAGRAPH)
        transitions.put(PARAGRAPH, LINE)
        transitions.put(LINE, WORD)
        transitions.put(WORD, LETTER)
    }

    public NodeType transition() {
        transitions.get(this)
    }

    public static NodeType transitionFrom(NodeType from){
        return transitions.get(from)
    }
}
