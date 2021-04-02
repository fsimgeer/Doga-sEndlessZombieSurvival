package scripts;

import doa.engine.scene.DoaComponent;
import doa.engine.task.DoaTasker;
import gameplay.weapon.IWeapon;

public class BulletLife extends DoaComponent {

	private static final long serialVersionUID = 4645812150809084549L;

	public BulletLife(IWeapon weapon) {
		DoaTasker.executeLater(() -> {
			if (getOwner().getScene() != null) {
				getOwner().getScene().remove(getOwner());
			}
		}, weapon.getBulletLife());
	}
}
