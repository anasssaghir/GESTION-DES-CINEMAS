package org.cinema.entities;

import org.springframework.data.rest.core.config.Projection;
import java.util.Collection;

@Projection(name = "p4", types = {User.class})
public interface UserProjection {
    public Long getId();
    public String getEmail();
    public String getFirstName();
    public String getLastName();
    public Collection<Role> getRoles();
}
