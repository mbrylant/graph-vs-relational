package org.mbrylant.tests.graphs.relationaldb

import com.thoughtworks.xstream.XStream
import org.apache.log4j.Logger
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mbrylant.tests.graphs.api.model.INode
import org.mbrylant.tests.graphs.api.model.SaveAndLoadResult
import org.mbrylant.tests.graphs.relationaldb.model.Node
import org.mbrylant.tests.graphs.api.model.NodeType
import org.mbrylant.tests.graphs.relationaldb.services.INodeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.transaction.TransactionConfiguration
import static org.mbrylant.tests.graphs.utils.Tests.*
import static org.mbrylant.tests.graphs.utils.Graphs.*

@ContextConfiguration(locations = "classpath:relational-application-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = false)
class RelationalSaveAndLoadTest {

    private final static Logger LOG = Logger.getLogger(RelationalSaveAndLoadTest)

    static class LoadRelationalTestRunner implements Runnable {
        private INodeService service

        private Long id

        INode getNode() { return node }

        private INode node

        LoadRelationalTestRunner(INodeService service, Long id) {
            this.service = service
            this.id = id
        }

        @Override
        void run() { node = service.load(id) }
    }

    static class PersistRelationalTestRunner implements Runnable {

        private INodeService service

        private INode inNode

        INode getNode() { return outNode }

        private INode outNode

        PersistRelationalTestRunner(INodeService service, INode node) {
            this.service = service
            this.inNode = node
        }

        @Override
        void run() { outNode = service.create(inNode) }
    }

    @Autowired
    private INodeService service

    private XStream stream = new XStream()

    @Before
    void setup() {}

    @Test
    def void persistAndLoadSmallGraph() {
        def results = (1..3).collect { int width ->
            def relationalNode = generateGraph(new Node().setNodeType(NodeType.DOCUMENT), width, false)
            println((relationalNode as Node).prettyPrint())
            LOG.info("Running test for width ${width}")
            def persistRelationalTestRunner = new PersistRelationalTestRunner(service, relationalNode)
            def save = timeExecution(persistRelationalTestRunner)
            def loadRelationalTestRunner = new LoadRelationalTestRunner(service, (persistRelationalTestRunner.node as Node).id)
            def load = timeExecution(loadRelationalTestRunner)
            new SaveAndLoadResult(width: width, save: save, load: load)
        }
    }

}
