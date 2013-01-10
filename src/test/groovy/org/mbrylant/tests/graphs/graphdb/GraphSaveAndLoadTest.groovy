package org.mbrylant.tests.graphs.graphdb

import com.orientechnologies.orient.core.id.ORecordId
import com.orientechnologies.orient.core.tx.OTransaction
import com.orientechnologies.orient.object.db.OObjectDatabaseTx
import com.thoughtworks.xstream.XStream
import org.apache.log4j.Logger
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mbrylant.tests.graphs.api.model.INode
import org.mbrylant.tests.graphs.api.model.SaveAndLoadResult
import org.mbrylant.tests.graphs.graphdb.model.Node
import org.mbrylant.tests.graphs.api.model.NodeType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.transaction.TransactionConfiguration
import static org.mbrylant.tests.graphs.utils.Tests.*
import static org.mbrylant.tests.graphs.utils.Graphs.*

@ContextConfiguration(locations = "classpath:graph-application-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = false)
class GraphSaveAndLoadTest {

    @Autowired
    private OObjectDatabaseTx db

    private final static Logger LOG = Logger.getLogger(GraphSaveAndLoadTest)

    private XStream stream = new XStream()

    static class LoadGraphTestRunner implements Runnable {

        private OObjectDatabaseTx db

        private String nodeId

        INode getNode() { return node }

        private INode node

        LoadGraphTestRunner(OObjectDatabaseTx db, String nodeId) {
            this.db = db
            this.nodeId = nodeId
        }

        @Override
        void run() { node = db.load(new ORecordId(nodeId)) }

    }

    static class PersistGraphTestRunner implements Runnable {

        private OObjectDatabaseTx db

        private INode node

        INode getNode() { return node }

        PersistGraphTestRunner(OObjectDatabaseTx db, INode node) {
            this.db = db
            this.node = node
        }

        @Override
        void run() {
            db.begin(OTransaction.TXTYPE.OPTIMISTIC);
            node = db.save(node)
            db.commit();

        }

    }

    @Before
    void setup() {
        db.open("admin", "admin");
        db.getEntityManager().registerEntityClasses("org.mbrylant.tests.graphs.graphdb.model");
    }

    @Test
    def void persistAndLoadSmallGraph() {
        def results = (1..3).collect { int width ->
            def graphNode = generateGraph(new Node().setNodeType(NodeType.DOCUMENT), width, false)
            println((graphNode as Node).prettyPrint())
            LOG.info("Running test for width ${width}")

            def persistGraphTestRunner = new PersistGraphTestRunner(db, graphNode)
            def save = timeExecution(persistGraphTestRunner)

            def loadGraphTestRunner = new LoadGraphTestRunner(db, (persistGraphTestRunner.node as Node).id)
            def load = timeExecution(loadGraphTestRunner)
            new SaveAndLoadResult(width: width, save: save, load: load)
        }
    }
}
