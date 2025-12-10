package barmoemoments;
import java.util.LinkedList;
import java.util.List;

public class VasoCerveza {

	private int id;
	private int tipo;

	public VasoCerveza(int id, int tipo) {
		super();
		this.id = id;
		this.tipo = tipo;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return "VasoCerveza [id=" + id + ", tipo=" + tipo + "]";
	}

}

class Camarero {

	private String nombre;
	private List<VasoCerveza> Vasos = new LinkedList<>();

	public Camarero(String nombre) {
		super();
		this.nombre = nombre;

		for (int i = 0; i < 3; i++) {
			int tipoAleatorio = (int)(Math.random() * 2); 
			VasoCerveza vaso = new VasoCerveza(i, tipoAleatorio);
			Vasos.add(vaso);
		}
	}

	public synchronized VasoCerveza servirCerveza() {

		if (Vasos.isEmpty()) {
			System.out.println("No hay vasos disponibles");
			return null;
		}

		int indice = (int) (Math.random() * Vasos.size());
		VasoCerveza vasoSeleccionado = Vasos.remove(indice);

		System.out.println("Sirviendo vaso: " + vasoSeleccionado);

		return vasoSeleccionado;
	}

	public synchronized void devolverCerveza(VasoCerveza vaso) {
		Vasos.add(vaso);
	}

	public void contarVasos() {
		int numerovasos = 0;

		for (VasoCerveza vasillos : Vasos) {
			numerovasos++;
		}

		System.out.println("Hay " + numerovasos + " vasos");
	}
}

class HilosClientes extends Thread {

	private Camarero camarero;
	private double litrosTotales = 0;

	public HilosClientes(String nombre, Camarero camarero) {
		super();
		setName(nombre);
		this.camarero = camarero;
	}

	public void run() {

		System.out.println("El cliente " + getName() + " está ejecutándose.");

		while(true) {

			
			VasoCerveza vaso = camarero.servirCerveza();

			if(vaso != null) {

				
				System.out.println(getName() + " está bebiendo cerveza del vaso " + vaso.getId());

				
				litrosTotales += 0.568;

				System.out.println(getName() + " lleva " + litrosTotales + " litros bebidos.");

				
				camarero.devolverCerveza(vaso);
			}

			
			try {
				int tiempo = (int)(Math.random() * 751) + 250;
				Thread.sleep(tiempo);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class Main {

	public static void main(String[] args) {

		Camarero camarero = new Camarero("Mou");

		HilosClientes homer = new HilosClientes("Homer", camarero);
		HilosClientes barney = new HilosClientes("Barney", camarero);
		HilosClientes carl = new HilosClientes("Carl", camarero);
		HilosClientes lenny = new HilosClientes("Lenny", camarero);
		HilosClientes lurleen = new HilosClientes("Lurleen", camarero);

		homer.start();
		barney.start();
		carl.start();
        lenny.start();
		lurleen.start();
	}
}
