import java.util.concurrent.Callable;

public class Klient implements Callable<String> {

	private static int numer = 0;
	private Gabinet poczekalnia;

	public Klient (Gabinet poczekalnia) {
		this.poczekalnia = poczekalnia;
	}

	public String call() {
		String wynik = poczekalnia.golenieKlient(++numer);
		System.out.println(wynik);
		return wynik;
	}
}