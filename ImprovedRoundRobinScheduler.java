import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

class process {
	String ID = "";
	int serviceTime = -1;
	int A = -1;
	int startTime = -1;
	int initialServiceTime=-1;


	public void setData(String newID, int newServiceTime, int newIntA) {
		ID = newID;
		serviceTime = newServiceTime;
		initialServiceTime= newServiceTime;
		A = newIntA;
	}

	public void setStartTime(int time) {
		startTime = time;
	}
	
	public void setServiceTime(int newServiceTime) {
		serviceTime = newServiceTime;
	}
}

class queueSort implements Comparator<process>{
	@Override
    public int compare(process o1, process o2) {
        return Integer.compare(o1.serviceTime, o2.serviceTime);
    }
}
public class ImprovedRoundRobinScheduler {

	public static void sortedandMinQRR(LinkedList<process> pwaitQ, int numProcesses, int quantum, int switchtime) {
		// Start CPU Scheduling
		LinkedList<process> pQ = new LinkedList<>();
		float turntimes = 0;
		int conswitch = 0;
		float avgWaitTime=0;
		// Create Time value
		int time = 0;
		// Set Finished flag
		boolean finished = false;
		while (finished == false) {
			boolean prsWaiting = false;
			for (int i = 0; i < pwaitQ.size(); i++) {
				if (pwaitQ.get(i).A <= time) {
					pQ.add(pwaitQ.get(i));
					pwaitQ.remove(i);
				}
			}

			for (int i = 0; i < pQ.size(); i++) {
				// if process has arrived
				// if (pQ.get(i).A <= time) {

				prsWaiting = true;
				if (pQ.get(i).startTime == -1) {
					pQ.get(i).setStartTime(time);
				}
				// quantum=pQ.get(i).serviceTime;
				time = time + quantum + switchtime;
				conswitch++;
				// if process hasn't been started yet

				pQ.get(i).setServiceTime(pQ.get(i).serviceTime - quantum);
				if ((pQ.get(i).serviceTime <= 0)) {
					// Do not waste time if finished during quantum
					time = time - Math.abs(pQ.get(i).serviceTime);

					// pID, print start time, end time, other calculations
					int finishTime = time - switchtime;
					// System.out.println(pQ.get(i).ID + " Finished at: "+ (finishTime));
					int initialWaitTime = pQ.get(i).startTime - pQ.get(i).A;
					// System.out.println("Initial Wait time: "+ initialWaitTime);
					int totalWaitTime = (finishTime - pQ.get(i).initialServiceTime) - pQ.get(i).A;
					avgWaitTime=avgWaitTime+totalWaitTime;
					// System.out.println("Total wait time: "+ totalWaitTime);
					turntimes = turntimes + (finishTime - pQ.get(i).A);
					// System.out.println("Turnaround time: "+ (finishTime-pQ.get(i).A));
					// System.out.println("~~~~~~~~~~~~~");

					pQ.remove(i);
					i = i - 1;
				}

				// }

			}
			// If there are no processes waiting, increment time
			if (prsWaiting == false) {
				time++;
			}

			// End if empty
			if (pwaitQ.isEmpty() && pQ.isEmpty()) {
				// if(pQ.isEmpty()) {
				finished = true;
				System.out.println("All processes finished at: " + time);
				turntimes = turntimes / numProcesses;
				avgWaitTime=avgWaitTime / numProcesses;
				System.out.println("Average Turnaround time: " + (turntimes));
				System.out.println("Average Wait time: " + (avgWaitTime));
				System.out.println("Context Switches: " + conswitch);
				break;
			}

			// After each cycle processes in the ready queue are
			// arranged in the ascending order of their remaining burst time and CPU is
			// allocated to the
			// processes using RR scheduling with time quantum value equal to the burst time
			// of first process in
			// the ready queue.
			Collections.sort(pQ, new queueSort());
			try {
				quantum = pQ.get(0).serviceTime;
			} catch (Exception ex) {

			}
		}
	}

	public static void sortedRR(LinkedList<process> pwaitQ, int numProcesses, int quantum, int switchtime) {
		// Start CPU Scheduling
		LinkedList<process> pQ = new LinkedList<>();
		float turntimes = 0;
		int conswitch=0;
		float avgWaitTime=0;
		// Create Time value
		int time = 0;
		// Set Finished flag
		boolean finished = false;
		while (finished == false) {
			boolean prsWaiting = false;
			for (int i = 0; i < pwaitQ.size(); i++) {
				if (pwaitQ.get(i).A <= time) {
					pQ.add(pwaitQ.get(i));
					pwaitQ.remove(i);
				}
			}

			for (int i = 0; i < pQ.size(); i++) {
				// if process has arrived
				// if (pQ.get(i).A <= time) {

				prsWaiting = true;
				if (pQ.get(i).startTime == -1) {
					pQ.get(i).setStartTime(time);
				}
				time = time + quantum + switchtime;
				conswitch++;
				// if process hasn't been started yet

				pQ.get(i).setServiceTime(pQ.get(i).serviceTime - quantum);
				if ((pQ.get(i).serviceTime <= 0)) {
					// Do not waste time if finished during quantum
					time = time - Math.abs(pQ.get(i).serviceTime);

					// pID, print start time, end time, other calculations
					int finishTime = time - switchtime;
					// System.out.println(pQ.get(i).ID + " Finished at: "+ (finishTime));
					int initialWaitTime = pQ.get(i).startTime - pQ.get(i).A;
					// System.out.println("Initial Wait time: "+ initialWaitTime);
					int totalWaitTime = (finishTime - pQ.get(i).initialServiceTime) - pQ.get(i).A;
					avgWaitTime=avgWaitTime+totalWaitTime;
					// System.out.println("Total wait time: "+ totalWaitTime);
					turntimes = turntimes + (finishTime - pQ.get(i).A);
					// System.out.println("Turnaround time: "+ (finishTime-pQ.get(i).A));
					// System.out.println("~~~~~~~~~~~~~");

					pQ.remove(i);
					i = i - 1;
				}

				// }

			}
			// If there are no processes waiting, increment time
			if (prsWaiting == false) {
				time++;
			}

			// End if empty
			if (pwaitQ.isEmpty() && pQ.isEmpty()) {
				// if(pQ.isEmpty()) {
				finished = true;
				System.out.println("All processes finished at: " + time);
				turntimes = turntimes / numProcesses;
				avgWaitTime=avgWaitTime / numProcesses;
				System.out.println("Average Turnaround time: " + (turntimes));
				System.out.println("Average Wait time: " + (avgWaitTime));
				System.out.println("Context Switches: " +conswitch);
				break;
			}

			// After each cycle processes in the ready queue are
			// arranged in the ascending order of their remaining burst time and CPU is
			// allocated to the
			// processes using RR scheduling with time quantum value equal to the burst time
			// of first process in
			// the ready queue.
			Collections.sort(pQ, new queueSort());
		}
	}

	public static void RR(LinkedList<process> pwaitQ, int numProcesses, int quantum, int switchtime) {
		// Start CPU Scheduling
		LinkedList<process> pQ = new LinkedList<>();
		float turntimes = 0;
		int conswitch=0;
		float avgWaitTime=0;
		// Create Time value
		int time = 0;
		// Set Finished flag
		boolean finished = false;
		while (finished == false) {
			boolean prsWaiting = false;
			for (int i = 0; i < pwaitQ.size(); i++) {
				if (pwaitQ.get(i).A <= time) {
					pQ.add(pwaitQ.get(i));
					pwaitQ.remove(i);
				}
			}
			for (int i = 0; i < pQ.size(); i++) {

				// if process has arrived
				// if (pQ.get(i).A <= time) {
				prsWaiting = true;
				if (pQ.get(i).startTime == -1) {
					pQ.get(i).setStartTime(time);
				}
				time = time + quantum + switchtime;
				conswitch++;
				// if process hasn't been started yet

				pQ.get(i).setServiceTime(pQ.get(i).serviceTime - quantum);
				if ((pQ.get(i).serviceTime <= 0)) {
					// Do not waste time if finished during quantum
					time = time - Math.abs(pQ.get(i).serviceTime);

					// pID, print start time, end time, other calculations
					int finishTime = time - switchtime;
					// System.out.println(pQ.get(i).ID + " Finished at: "+ (finishTime));
					int initialWaitTime = pQ.get(i).startTime - pQ.get(i).A;
					// System.out.println("Initial Wait time: "+ initialWaitTime);
					int totalWaitTime = (finishTime - pQ.get(i).initialServiceTime) - pQ.get(i).A;
					avgWaitTime=avgWaitTime+totalWaitTime;
					// System.out.println("Total wait time: "+ totalWaitTime);
					turntimes = turntimes + (finishTime - pQ.get(i).A);
					// System.out.println("Turnaround time: "+ (finishTime-pQ.get(i).A));
					// System.out.println("~~~~~~~~~~~~~");

					pQ.remove(i);
					i = i - 1;
				}

				// }

			}
			// If there are no processes waiting, increment time
			if (prsWaiting == false) {
				time++;
			}

			// End if empty
			if (pwaitQ.isEmpty() && pQ.isEmpty()) {
				finished = true;
				System.out.println("All processes finished at: " + time);

				turntimes = turntimes / numProcesses;
				avgWaitTime=avgWaitTime / numProcesses;
				System.out.println("Average Turnaround time: " + (turntimes));
				System.out.println("Average Wait time: " + (avgWaitTime));
				System.out.println("Context Switches: " +conswitch);
			}

		}
	}

	public static void sortedminQThreshold(LinkedList<process> pwaitQ, int numProcesses, int quantum, int switchtime) {
		// Start CPU Scheduling
		LinkedList<process> pQ = new LinkedList<>();
		int initialQuantum=quantum;
		float turntimes = 0;
		int conswitch = 0;
		float avgWaitTime=0;
		// Create Time value
		int time = 0;
		// Set Finished flag
		boolean finished = false;
		while (finished == false) {
			boolean prsWaiting = false;
			for (int i = 0; i < pwaitQ.size(); i++) {
				if (pwaitQ.get(i).A <= time) {
					pQ.add(pwaitQ.get(i));
					pwaitQ.remove(i);
				}
			}

			for (int i = 0; i < pQ.size(); i++) {
				// if process has arrived
				// if (pQ.get(i).A <= time) {

				prsWaiting = true;
				if (pQ.get(i).startTime == -1) {
					pQ.get(i).setStartTime(time);
				}
				// quantum=pQ.get(i).serviceTime;
				time = time + quantum + switchtime;
				conswitch++;
				// if process hasn't been started yet

				pQ.get(i).setServiceTime(pQ.get(i).serviceTime - quantum);
				if ((pQ.get(i).serviceTime <= 0)) {
					// Do not waste time if finished during quantum
					time = time - Math.abs(pQ.get(i).serviceTime);

					// pID, print start time, end time, other calculations
					int finishTime = time - switchtime;
					// System.out.println(pQ.get(i).ID + " Finished at: "+ (finishTime));
					int initialWaitTime = pQ.get(i).startTime - pQ.get(i).A;
					// System.out.println("Initial Wait time: "+ initialWaitTime);
					int totalWaitTime = (finishTime - pQ.get(i).initialServiceTime) - pQ.get(i).A;
					avgWaitTime=avgWaitTime+totalWaitTime;
					// System.out.println("Total wait time: "+ totalWaitTime);
					turntimes = turntimes + (finishTime - pQ.get(i).A);
					// System.out.println("Turnaround time: "+ (finishTime-pQ.get(i).A));
					// System.out.println("~~~~~~~~~~~~~");

					pQ.remove(i);
					i = i - 1;
				}

				// }

			}
			// If there are no processes waiting, increment time
			if (prsWaiting == false) {
				time++;
			}

			// End if empty
			if (pwaitQ.isEmpty() && pQ.isEmpty()) {
				// if(pQ.isEmpty()) {
				finished = true;
				System.out.println("All processes finished at: " + time);
				turntimes = turntimes / numProcesses;
				avgWaitTime=avgWaitTime / numProcesses;
				System.out.println("Average Turnaround time: " + (turntimes));
				System.out.println("Average Wait time: " + (avgWaitTime));
				System.out.println("Context Switches: " + conswitch);
				break;
			}

			// After each cycle processes in the ready queue are
			// arranged in the ascending order of their remaining burst time and CPU is
			// allocated to the
			// processes using RR scheduling with time quantum value equal to the burst time
			// of first process in
			// the ready queue.
			Collections.sort(pQ, new queueSort());
			try {
				if((pQ.get(0).serviceTime)>initialQuantum) {
					quantum = pQ.get(0).serviceTime;
				}else {
					quantum=initialQuantum;
				}
				
			} catch (Exception ex) {

			}
		}
	}
	
	public static void main(String args[]) {
		int numProcesses = 1000;
		int quantum = 3;
		int switchTime = 1;

		LinkedList<process> pQueue = new LinkedList<>();
		LinkedList<process> sortedandMinQpQueue = new LinkedList<>();
		LinkedList<process> sortedandMinQthresholdpQueue = new LinkedList<>();
		LinkedList<process> sortedpQueue = new LinkedList<>();

		// Create the processes
		for (int i = 0; i < numProcesses; i++) {
			// Create a separate process for different RRs
			process prs = new process();
			process sortedandMinQprs = new process();
			process sortedandMinQthresholdprs = new process();
			process sortedprs = new process();
			int aTime = 0;
			try {
				// min is inclusive, max is exclusive so +1
				aTime = pQueue.get(i - 1).A + ThreadLocalRandom.current().nextInt(3, 5 + 1);
			} catch (Exception ex) {
				aTime = ThreadLocalRandom.current().nextInt(3, 5 + 1);
			}
			int serviceTime = ThreadLocalRandom.current().nextInt(2, 7 + 1);
			prs.setData("p" + i, serviceTime, aTime);
			sortedandMinQprs.setData("p" + i, serviceTime, aTime);
			sortedandMinQthresholdprs.setData("p" + i, serviceTime, aTime);
			sortedprs.setData("p" + i, serviceTime, aTime);
			pQueue.add(prs);
			sortedandMinQpQueue.add(sortedandMinQprs);
			sortedandMinQthresholdpQueue.add(sortedandMinQthresholdprs);
			sortedpQueue.add(sortedprs);
		}

		/*for (int i = 0; i < numProcesses; i++) {
			System.out.println(pQueue.get(i).ID + " Arrival time " + pQueue.get(i).A + " Service time "
					+ pQueue.get(i).serviceTime);
		
		}*/
		System.out.println("~~~~~~~~~~~~~");

		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("Regular RR");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		RR(pQueue, numProcesses, quantum, switchTime);
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("sorted RR");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		sortedRR(sortedpQueue, numProcesses, quantum, switchTime);
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("sorted and Min Quantum RR");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		sortedandMinQRR(sortedandMinQpQueue, numProcesses, quantum, switchTime);
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("sorted and Min Quantum RR with Threshold");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		sortedminQThreshold(sortedandMinQthresholdpQueue, numProcesses, quantum, switchTime);
	}
}
