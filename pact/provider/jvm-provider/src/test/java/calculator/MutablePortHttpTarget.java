package calculator;

import au.com.dius.pact.model.PactSource;
import au.com.dius.pact.provider.ProviderInfo;
import au.com.dius.pact.provider.junit.target.HttpTarget;

import java.util.function.Supplier;

public class MutablePortHttpTarget extends HttpTarget {

	private Supplier<Integer> portSupplier;

	public MutablePortHttpTarget(Supplier<Integer> portSupplier) {
		super(-1);
		this.portSupplier = portSupplier;
	}

	@Override
	protected ProviderInfo getProviderInfo(PactSource source) {
		ProviderInfo providerInfo = super.getProviderInfo(source);
		providerInfo.setPort(portSupplier.get());
		return providerInfo;
	}
}
