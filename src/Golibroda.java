import java.util.concurrent.Callable;

public class Golibroda implements Callable<String> {

	private Gabinet poczekalnia;

	public Golibroda(Gabinet gabinet) {
		this.poczekalnia = gabinet;
	}

	public String call() {

		while (true) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			poczekalnia.golenieGolibroda();
		}
	}
}
