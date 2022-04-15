package com.cms.database.converter;

import java.time.Instant;

public interface DepartmentConverter {
    Long getId();
    String getName();
    String getQaName();
    Instant getStartDate();
    Instant getClosureDateIdea();
    Instant getClosureDate();
}
