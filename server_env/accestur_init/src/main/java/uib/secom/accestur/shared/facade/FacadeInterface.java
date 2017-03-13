package uib.secom.accestur.shared.facade;

import uib.secom.accestur.shared.model.DomainObject;

public interface FacadeInterface<T extends DomainObject> {

	T read(final T domainObject);

	T update(final T domainObject);

}