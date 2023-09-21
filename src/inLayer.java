public class inLayer extends Layer{
    inLayer(int size) {
        super(null, size);
    }
    Matrix run(Matrix input){
        if(input ==null){
            lastOut = bias.sigm();
            return child.run(bias.sigm());
        }
        lastOut = input.sigm();
        return child.run(input.sigm());
    }
}
