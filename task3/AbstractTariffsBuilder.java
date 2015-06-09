package tasks.task3;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public abstract class AbstractTariffsBuilder {
	
	protected Set<Tariff> tariffs;
	
	public AbstractTariffsBuilder() {
		tariffs = new HashSet<Tariff>();
	}
	
	public AbstractTariffsBuilder(Set<Tariff> tariffs) {
		this.tariffs = tariffs;
	}
	
	public Set<Tariff> getTariffs() {
		return tariffs;
	}
	
	public LinkedList<Tariff> getSortedTariffs() {
		LinkedList<Tariff> sortedTariffs = new LinkedList<>();
		sortedTariffs.addAll(tariffs);
		Collections.sort(sortedTariffs, new Comparator<Tariff>() {
			public int compare(Tariff tariff1, Tariff tariff2) {
				return tariff2.getPayroll() - tariff1.getPayroll();
			}
		});
		return sortedTariffs;
	}
	
	public abstract void buildSetTariffs(String fileName);
}
