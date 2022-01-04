public class Main {

    private static final int FULL_SIZE = 10000000;
    private static final int HALF_SIZE = FULL_SIZE / 2;

    public static void main(String s[]) {
        Main o = new Main();
        o.firstMethod();
        o.secondMethod();
    }

    public float[] calculation(float[] arr) {
        for (int i = 0; i < arr.length; i++)
            arr[i] = (float) (arr[i] * Math.sin(0.2f + arr[i] / 5) * Math.cos(0.2f + arr[i] / 5) * Math.cos(0.4f + arr[i] / 2));
        return arr;
    }

    public void firstMethod() {
        float[] arr = new float[FULL_SIZE];
        for (int i = 0; i < arr.length; i++) arr[i] = 1.0f;
        long startTime = System.currentTimeMillis();
        calculation(arr);
        System.out.println("One thread time: " + (System.currentTimeMillis() - startTime) + " ms.");
    }

    public void secondMethod() {
        float[] arr = new float[FULL_SIZE];
        float[] arr1 = new float[HALF_SIZE];
        float[] arr2 = new float[HALF_SIZE];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1.0f;
        }

        long startTime = System.currentTimeMillis();
        System.arraycopy(arr, 0, arr1, 0, HALF_SIZE);
        System.arraycopy(arr, HALF_SIZE, arr2, 0, HALF_SIZE);

        new Thread() {
            public void run() {
                float[] a1 = calculation(arr1);
                System.arraycopy(a1, 0, arr1, 0, a1.length);
            }
        }.start();

        new Thread() {
            public void run() {
                float[] a2 = calculation(arr2);
                System.arraycopy(a2, 0, arr2, 0, a2.length);
            }
        }.start();

        System.arraycopy(arr1, 0, arr, 0, HALF_SIZE);
        System.arraycopy(arr2, 0, arr, HALF_SIZE, HALF_SIZE);
        System.out.println("Two threads ends with: " + (System.currentTimeMillis() - startTime) + " ms.");
    }

}
