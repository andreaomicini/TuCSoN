import alice.tuplecentre.tucson.api.exceptions.TucsonInvalidAgentIdException;
import asynchAPI.MasterAgent;
import asynchAPI.PrimeCalculator;
import diningPhilos.DiningPhilosophersTest;
import distributedDiningPhilos.DDiningPhilosophersTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Classic Dining Philosophers coordination problem tackled by adopting a clear
 * separation copyOf concerns between coordinables (philosophers) and coordination
 * medium (table) thanks to TuCSoN ReSpecT tuple centres programmability.
 *
 * @author ste (mailto: s.mariani@unibo.it)
 * @author (contributor) Stefano Bernagozzi (stefano.bernagozzi@studio.unibo.it)
 */
@TestMethodOrder(MethodOrderer.Random.class)
public class RunTests {

    @Test
    public void testDDinigPhilo(){

        System.out.println("[[[initializing testDDinigPhilo]]]");

        boolean ended = false;
        try {
            CountDownLatch latch = new CountDownLatch(5);
            new DDiningPhilosophersTest("boot", latch).go();
            try {
                if (latch.await(400, TimeUnit.SECONDS)) {
                    ended = true;
                    System.out.println("done");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } catch (final TucsonInvalidAgentIdException e) {
            e.printStackTrace();
        }
        assertTrue(ended);
    }

    @Test
    public void testPrimeCalculator() {
        boolean ended = false;
        System.out.println("[[[initializing testPrimeCalculator]]]");
        try {
            final int nOfWorkers = 3;
            //this due to master calculate up to 50000 with 1000 step
            CountDownLatch primeIntervalsCalculated = new CountDownLatch(50);
            Logger.getLogger("PrimeCalculationLauncher").info(
                    "Starting Master Agent");
            new MasterAgent("master", nOfWorkers, primeIntervalsCalculated).go();
            for (int i = 0; i < nOfWorkers; i++) {
                Logger.getLogger("PrimeCalculationLauncher").info(
                        "Starting Prime Calculator n. " + i);
                new PrimeCalculator("worker" + i).go();
            }
            try {
                if (primeIntervalsCalculated.await(300, TimeUnit.SECONDS)) {
                    ended = true;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } catch (final TucsonInvalidAgentIdException e) {
            e.printStackTrace();
        }
        assertTrue(ended);
    }

    @Test
    void testTimedPhilo() {
        boolean ended = false;
        try {
            CountDownLatch latch = new CountDownLatch(5);
            new DiningPhilosophersTest("boot", latch).go();
            try {
                if (latch.await(200, TimeUnit.SECONDS)) {
                    ended = true;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } catch (final TucsonInvalidAgentIdException e) {
            e.printStackTrace();
        }
        assertTrue(ended);
    }

    @Test
    void testPhilo() {
        boolean ended = false;
        try {
            CountDownLatch latch = new CountDownLatch(5);
            new DiningPhilosophersTest("boot", latch).go();
            try {
                if (latch.await(240, TimeUnit.SECONDS)) {
                    ended = true;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } catch (final TucsonInvalidAgentIdException e) {
            e.printStackTrace();
        }
        assertTrue(ended);
    }
}
