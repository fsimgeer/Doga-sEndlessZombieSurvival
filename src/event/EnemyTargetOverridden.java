package event;

import doa.engine.scene.elements.DoaTransform;

public class EnemyTargetOverridden implements IEvent {

	private DoaTransform newTarget;

	public EnemyTargetOverridden(DoaTransform newTarget) {
		this.newTarget = newTarget;
	}

	@Override
	public Object getEventData() {
		return newTarget;
	}

}
