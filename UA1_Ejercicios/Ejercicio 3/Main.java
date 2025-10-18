public class Main {
    public static void main(String[] args) {

        //Probamos que funciona
        System.out.println("Probando funcionamiento");

        //Miramos si tenemos algo por argumentos
        if(args.length<1){
            System.exit(1);
        }

        //Usamos un try, en caso de error quiere decir que no es un número
        try{
            int posibleNumber=Integer.parseInt(args[0]);

            //Miramos si es negativo
            if(posibleNumber<0){
                System.exit(3);
            }
            //Aquí miramos si no es un número pues anteriormente daría error dando lugar al catch
        } catch(Exception e){
            System.exit(2);
        }
        //En cualquier caso devuelve 0 por eso está al final y fuera de catch o if
        System.exit(0);
    }
}