package io.ron.demo.validator.use.validator.group;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence({ Update.class, Default.class })
public interface OrderedGroup {

}
