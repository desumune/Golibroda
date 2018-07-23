import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Gabinet {

	private int klienci = 0;
	private final int miejscaWGabinecie = 5;
	private int wolneMiejsca = miejscaWGabinecie;
	private boolean golibrodaWolny = true;

	private Lock lock = new ReentrantLock();
	private Condition doGolenia = lock.newCondition();
	private Condition poGoleniu = lock.newCondition();

	void golenieGolibroda() {
		lock.lock();
		try {
			while (golibrodaWolny == true) {
				doGolenia.await();
			}
			klienci--;
			System.out.println("Golibroda ogolił klienta. Wolne miejsca: " + wolneMiejsca);
			golibrodaWolny = true;
			poGoleniu.signal();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
			if (klienci == 0) {
				System.out.println("Poczekalnia jest pusta. Golibroda śpi w gabinecie.");
				System.exit(0);
			}
		}
	}

	String golenieKlient(int numer) {
		lock.lock();
		try {
			if (klienci <= miejscaWGabinecie) {
				klienci++;
				System.out.println("Klient #" + numer + " właśnie wszedł do poczekalni. Wolne miejsca: " + wolneMiejsca);
				wolneMiejsca--;
				while (golibrodaWolny == false) {
					poGoleniu.await();
				}
				golibrodaWolny = false;
				wolneMiejsca++;

				doGolenia.signal();
				return "Klient #" + numer + " zasiadł do golenia";
			} else {
				return "Klient #" + numer + " nie może zająć miejsca w poczekalni ponieważ wolne miejsca: " + wolneMiejsca;
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		return "";
	}
}