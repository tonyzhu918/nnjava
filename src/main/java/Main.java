import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

// 注意类名必须为 Main, 不要有任何 package xxx 信息
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int row = in.nextInt();
        int[][] matrix = new int[row][3];

        Set<Worker> workers = new HashSet<Worker>();

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < 3; j++) {
                matrix[i][j] = in.nextInt();
            }
            Worker e = new Worker(matrix[i][0], matrix[i][1], matrix[i][2]);
            workers.add(e);
            Worker mgr = new Worker(matrix[i][1], Integer.MIN_VALUE, 0);
            if (!workers.contains(mgr)) {
                workers.add(mgr);
            }
        }

        Worker boss = null;
        for (Worker e : workers) {
            if (e.mgrId == Integer.MIN_VALUE) {
                boss = e;
                break;
            }
        }

        int bossSalary = getBossSalary(boss, workers);
        System.out.println(boss.workerId + " " + bossSalary);

    }

    public static int getBossSalary(Worker mgr, Set<Worker> workers) {
        int salary = 0;
        salary += mgr.salary;
        for (Worker e : workers) {
            if (e.mgrId == mgr.workerId) {
                salary += (getBossSalary(e, workers) / 100) * 15;
            }
        }
        return salary;
    }
}

class Worker {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Worker worker = (Worker) o;
        return workerId == worker.workerId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(workerId);
    }

    int workerId;
    int mgrId = Integer.MIN_VALUE;
    int salary = 0;

    public Worker(int workerId, int mgrId, int salary) {
        this.workerId = workerId;
        this.mgrId = mgrId;
        this.salary = salary;
    }
}