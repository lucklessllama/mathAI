import java.util.Random;

public class Matrix {
    Random random=new Random();
    int sizeX;
    int sizeY;
    float[][]matrix;
    public Matrix cloneThis(){
        float[][] newMatrix=new float[sizeX][sizeY];
        for(int x=0;x<sizeX;x++)
        {
            newMatrix[x]=matrix[x].clone();
        }
        return new Matrix(newMatrix);
    }
    Matrix(String m){
        String[] m2=m.split(",");
        sizeX=Integer.parseInt(m2[0]);
        sizeY=Integer.parseInt(m2[1]);
        matrix=new float[sizeX][sizeY];
        for(int x=0;x<sizeX;x++){
            for(int y=0;y<sizeY;y++){
                matrix[x][y]=Float.parseFloat(m2[2+y+x*sizeY]);
            }
        }
    }
    Matrix(float[][] m){
        matrix=m;
        sizeX=matrix.length;
        sizeY=matrix[0].length;
    }
    Matrix(int x,int y,boolean ran){
        matrix=new float[x][y];
        sizeX=x;
        sizeY=y;
        if(ran){
            for (int x1 =0;x1<x;x1++){
                for (int y1 =0;y1<y;y1++){
                    matrix[x1][y1]=random.nextFloat(0,1f);
                }
            }
        }
    }

    public float meansq(Matrix cor) {
        Matrix A=this.oneHot();
        A=A.squishY();
        for(int x=0;x<A.sizeX;x++)
        {
            A.matrix[x][0]=((float)sizeY/4)/(A.matrix[x][0]+1);
        }
        Matrix subeb=this.sigm().subtract(cor);
        //square each element
        Matrix muled=subeb.sqAll();
        // get the mean and return
        return muled.mean();//*(A.squishY().rotate().squishY().matrix[0][0]);
    }
    public Matrix multiply(Matrix m){
        //System.out.println("("+sizeX+","+sizeY+")*("+m.sizeX+","+m.sizeY+")");
        Matrix output=new Matrix(sizeX,m.sizeY,false);
        if(sizeY==m.sizeX)
        {
            for(int x =0;x<sizeX;x++)
            {
                for(int y =0;y<m.sizeY;y++)
                {
                    float spot=0;
                    for (int g=0;g<m.sizeX;g++){
                        spot+=matrix[x][g]*m.matrix[g][y];
                    }
                    output.matrix[x][y]=spot;
                }
            }
        }
        else {System.out.println("error matrix sizes can't multiply");}
        return output;

    }
    public Matrix addBias(Matrix m) {
        Matrix rt=new Matrix(sizeX,sizeY,false);
        for (int x =0;x<sizeX;x++)
        {
            for (int y =0;y<sizeY;y++)
            {
                rt.matrix[x][y]=matrix[x][y]+m.matrix[0][y];
            }
        }
        return rt;

    }
    public Matrix subtractBias(Matrix m) {
        Matrix rt=new Matrix(sizeX,sizeY,false);
        for (int x =0;x<sizeX;x++)
        {
            for (int y =0;y<sizeY;y++)
            {
                rt.matrix[x][y]=matrix[x][y]-m.matrix[0][y];
            }
        }
        return rt;

    }
    public Matrix add(Matrix m){
        Matrix rt=new Matrix(sizeX,sizeY,false);
        for (int x =0;x<sizeX;x++)
        {
            for (int y =0;y<sizeY;y++)
            {
                rt.matrix[x][y]=matrix[x][y]+m.matrix[x][y];
            }
        }
        return rt;
    }
    public Matrix subtract(Matrix m){
        Matrix rt=new Matrix(sizeX,sizeY,false);
        for (int x =0;x<sizeX;x++)
        {
            for (int y =0;y<sizeY;y++)
            {
                rt.matrix[x][y]=matrix[x][y]-m.matrix[x][y];
            }
        }
        return rt;
    }
    public Matrix sqAll(){
        Matrix rt=new Matrix(sizeX,sizeY,false);
        for (int x =0;x<sizeX;x++)
        {
            for (int y =0;y<sizeY;y++)
            {
                rt.matrix[x][y]=(float)Math.pow(matrix[x][y],2);
            }
        }
        return rt;
    }//all spots to the power of 2
    public Matrix divideEach(float num){
        Matrix rt=new Matrix(sizeX,sizeY,false);
        for (int x =0;x<sizeX;x++) {
            for (int y =0;y<sizeY;y++) {
                rt.matrix[x][y]=matrix[x][y]/num;
            }
        }
        return rt;
    }
    public Matrix multiplyEach(float num){
        Matrix rt=new Matrix(sizeX,sizeY,false);
        for (int x =0;x<sizeX;x++) {
            for (int y =0;y<sizeY;y++) {
                rt.matrix[x][y]*=num;
            }
        }
        return rt;
    }
    public Matrix subtractEach(float num){
        Matrix rt=new Matrix(sizeX,sizeY,false);
        for (int x =0;x<sizeX;x++) {
            for (int y =0;y<sizeY;y++) {
                rt.matrix[x][y]-=num;
            }
        }
        return rt;
    }
    public float mean(){
        float rt=0;
        for (int x =0;x<sizeX;x++)
        {
            for (int y =0;y<sizeY;y++)
            {
                rt+=matrix[x][y];
            }
        }
        rt/=(sizeX*sizeY);
        return rt;
    }
    public Matrix oneHot() {
        //System.out.print("did the one");
        Matrix outMatrix=new Matrix(sizeX,sizeY,false);
        for(int x =0;x<sizeX;x++)
        {
            float highest=matrix[x][0];
            int bestY=0;
            for(int y =0;y<sizeY;y++)
            {
                if(matrix[x][y]>highest){
                    bestY=y;
                    highest=matrix[x][y];
                }
            }
            outMatrix.matrix[x][bestY]=1;
        }
        return outMatrix;

    }
    public Matrix sigm() {
        //System.out.print("did the other ");
        Matrix outMatrix=new Matrix(matrix);
        for(int x=0;x<sizeX;x++){
            for(int y=0;y<sizeY;y++){
                double bm=-matrix[x][y];
                outMatrix.matrix[x][y]=(float)(1/(1+Math.exp(bm)));
            }
        }
        return outMatrix;
    }
    public Matrix inSigm() {
        Matrix outMatrix=new Matrix(matrix);
        for(int x=0;x<sizeX;x++){
            for(int y=0;y<sizeY;y++){
                float bm=Math.abs(matrix[x][y]);
                outMatrix.matrix[x][y]=(float) (1/(1+(Math.exp(-bm))));
            }
        }

        return outMatrix;
    }
    public Matrix drivSigm() {
        Matrix outMatrix=new Matrix(matrix);
        for(int x=0;x<sizeX;x++){
            for(int y=0;y<sizeY;y++){
                float bm=Math.abs(matrix[x][y]);
                outMatrix.matrix[x][y]=(bm*(1-bm));
            }
        }
        //outMatrix.print();
        return outMatrix;
    }
    Matrix rotate() {
        Matrix output=new Matrix(sizeY,sizeX,false);
        for (int x =0;x<sizeX;x++) {
            for (int y =0;y<sizeY;y++) {
                output.matrix[y][x]=matrix[x][y];
            }
        }
        return output;
    }
    Matrix squishY(){
        Matrix rt =new Matrix(sizeX,1,false);
        for(int x=0;x<sizeX;x++){
            rt.matrix[x][0]=0;
            for(int y=0;y<sizeY;y++){
                rt.matrix[x][0]+=matrix[x][y];
            }
        }
        return rt;
    }

    Matrix randomW(float inprov)
    {
        float aj=0.02f;
        if(inprov<0.2f)
        {
            //System.out.println("243 "+inprov);
            aj=0.1f;
        }
        if(inprov>1)
        {
            aj=0.05f;
        }

        Matrix rt=this.cloneThis();
        for (int x =0;x<sizeX;x++){
            for (int y =0;y<sizeY;y++){
                rt.matrix[x][y]+=(float)(Math.floor(1000f*random.nextFloat(-aj,aj)))/1000f;
                //System.out.println((float)(Math.floor(1000f*random.nextFloat(-aj,aj)))/1000f);
                //rt.matrix[x][y]=Math.max(Math.min(rt.matrix[x][y],1),0);
            }
        }
        return rt;
    }
    void print()
    {
        for(int y=0;y<sizeY;y++){
            System.out.println();
            for(int x=0;x<sizeX;x++){
                System.out.print(matrix[x][y]+", ");
            }}
        System.out.println();

    }
}

