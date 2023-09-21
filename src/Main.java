public class Main {
    public static void main(String[] args) {
       Layer l1=new inLayer(10);
       Layer l2=new Layer(l1,20);
       l1.run(null).print();
    }
}