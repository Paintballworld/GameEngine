package com.itechtopus.tanks.factory;

import com.itechtopus.tanks.interfaces.Tanker;
import com.itechtopus.tanks.interfaces.field.Field;

public interface FieldFactory {

  Field createFieldCopyFor(Tanker player);

}
