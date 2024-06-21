package com.github.kill05.projectbta;

import com.github.kill05.projectbta.registry.EmcKey;

import java.util.List;

public interface ProjectPlayer extends IEmcHolder {

	List<EmcKey> getKnownItems();

	boolean knowsItem(EmcKey key);

	boolean learnItem(EmcKey key);

	boolean unlearnItem(EmcKey key);

	boolean hasTome();

}
