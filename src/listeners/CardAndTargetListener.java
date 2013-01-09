package listeners;

import java.util.EventListener;

import events.CancelChosenCardEvent;
import events.CardAndTargetChosenEvent;
import events.CardChosenEvent;

public interface CardAndTargetListener extends EventListener{

	public void cardAndTargetChosen(CardAndTargetChosenEvent event);
	public void cardChosen(CardChosenEvent event);
	public void cancelChosenCard(CancelChosenCardEvent event);
	
}
