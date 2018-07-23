import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

	//liczba klientów którzy odwiedzają gabinet golibrody
	private static final int KLIENCI = 15;
	private static ExecutorService pool = Executors.newFixedThreadPool(KLIENCI);
	private static CompletionService<String> service = new ExecutorCompletionService<String>(pool);

	public static void main(String[] args) {

		Gabinet gabinet = new Gabinet();

		try {			
			List<Future<String>> results = new ArrayList<>();
			results.add(service.submit(new Golibroda(gabinet)));
			
			//kolejni klienci wchodzą do gabinetu co 2s
			for (int i = 0; i < KLIENCI; i++) {
				results.add(service.submit(new Klient(gabinet)));
				Thread.sleep(2000);
			}

			pool.shutdown();
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

}
