
public class Worker {
    public static void main(String[] args) throws Exception {
        String name = args.length > 0 ? args[0] : "W";
        int seconds = args.length > 1 ? Integer.parseInt(args[1]) : 3;

        long start = System.currentTimeMillis();
        System.out.println("[" + name + "] START at " + start + " ms");
        System.out.println("[" + name + "] sleeping " + seconds + "s ...");

        Thread.sleep(seconds * 1000L);

        long end = System.currentTimeMillis();
        System.out.println("[" + name + "] END at " + end + " ms (dur: " + (end - start) + " ms)");
    }
}