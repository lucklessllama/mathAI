public class Layer {
    int size;
    Layer parent;
    Layer child;
    Matrix bias;
    Matrix weights;
    Matrix lastOut;
    Layer(Layer p, int size){
        parent=p;
        this.size=size;
        if(p==null){
            weights=new Matrix(1,size,true);
        }
        else {
            p.child=this;
            weights = new Matrix(p.size, size, true);
        }
        bias=new Matrix(1,size,true);
        weights.print();
    }
    Matrix run(Matrix in){
        Matrix out= in.multiply(weights);
        out=out.divideEach(size);
        out=out.addBias(bias);
        if(child == null)
            return out;
        return child.run(out.sigm());
    }
    Matrix backProp(){
        return null;
    }
}
