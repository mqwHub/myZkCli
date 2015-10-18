package zkcli;

import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class ZkOperation implements Watcher {

	private ZooKeeper zk;
	
	public ZkOperation(String ip, int port, int timeout) throws Exception {
		String connectionString = ip + ":" + port;
		zk = new ZooKeeper(connectionString, timeout, this);
	}
	
	public void process(WatchedEvent arg0) {
		
	}

	public void printTree() throws KeeperException, InterruptedException {
		String root = "/";
		printNode(root,0);
	}
	
	private void printNode(String node, int count) throws KeeperException, InterruptedException {
		String parent = node;
		List<String> nodes = zk.getChildren(node, this);
		if (!nodes.isEmpty()) {
			for (String s : nodes) {
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < count; i++) {
					sb.append(" ");
				}
				System.out.println(sb.append("+").append(s));
				int tmp = count + 1;
				if (parent.equals("/")) {				
					printNode(parent + s, tmp);					
				} else {
					printNode(parent + "/" + s, tmp);
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		String ip = "127.0.0.1";
		int port = 2181;
		ZkOperation opr = new ZkOperation(ip, port, 15000);
		opr.printTree();
	}
}
