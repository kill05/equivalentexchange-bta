package com.github.kill05.projectbta;

import com.github.kill05.projectbta.registry.EmcKey;

public interface ProjectPlayer extends IEmcHolder {

	boolean learnItem(EmcKey itemStack);

	boolean unlearnItem(EmcKey itemStack);

}
