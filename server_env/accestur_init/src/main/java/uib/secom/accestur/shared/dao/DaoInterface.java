package uib.secom.accestur.shared.dao;

import uib.secom.accestur.shared.model.DomainObject;

public interface DaoInterface<T extends DomainObject> {

	T read(final T domainObject);

	T update(final T domainObject);

}