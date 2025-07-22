package com.ceslopedevega.procesos;

public class LeerNombre {

	public static void main(String[] args) {		
		if(args.length!=1){
			System.out.println("Argumentos incorrectos en main()");
			System.exit(-1);
		}
		System.out.printf("Nombre: %s %n", args[0]);
		System.exit(1);
	}

}
