package uib.secom.accestur.shared.controller;

import uib.secom.accestur.shared.model.DomainObject;

public interface ControllerInterface<T extends DomainObject> {

	T read(final T domainObject);

	T update(final T domainObject);

}