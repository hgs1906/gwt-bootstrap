package com.github.gwtbootstrap.client.ui.incubator;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.ListBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;

import java.util.List;

/**
 *
 * Usage Example:<br/>
 * <ul><li>
 * Put following code into your ui.xml file:<br/>
 * <code>
 * &lt;bi:PickList ui:field="pickList" /&gt;
 * </code><br/><br/>
 *
 * </li><li>
 * Populate your picklist using {@link com.github.gwtbootstrap.client.ui.incubator.NameValuePair}:<br/>
 * <code>
 *     List<INameValuePair> nameValuePairs = new ArrayList<INameValuePair>();<br/>
 *     nameValuePairs.add(new NameValuePair("item 1", "item_1"));<br/>
 *     nameValuePairs.add(new NameValuePair("item 2", "item_2"));<br/>
 *     nameValuePairs.add(new NameValuePair("item 3", "item_3"));<br/>
 *     pickList.setLeftListElements(nameValuePairs);
 *     pickList.setRightListElements(nameValuePairs);
 * </code><br/>
 * </li></ul>
 *
 * Screenshot:
 * <br/>
 * <img src="http://gamenism.com/gwt/picklist.png"/>
 * <br/>
 *
 * User: Halil Karakose
 * Date: 10/18/13
 * Time: 3:53 PM
 *
 * @see NameValuePair
 */
public class PickList extends Composite {
    @UiField
    Button toRightButton;
    @UiField
    Button toLeftButton;
    @UiField
    ListBox leftList;
    @UiField
    ListBox rightList;

    public void setLeftListElements(List<? extends INameValuePair> elements) {
        populate(elements, leftList);
    }

    public void setRightListElements(List<? extends INameValuePair> elements) {
        populate(elements, rightList);
    }

    private void populate(List<? extends INameValuePair> leftListElements, ListBox listBox) {
        for (INameValuePair element : leftListElements) {
            listBox.addItem(element.name(), element.value());
        }
    }

    @UiHandler("toRightButton")
    public void toRightButtonClicked(ClickEvent event) {
        moveItem(leftList, rightList, event);

        if (leftList.getItemCount() == 0) {
            toRightButton.setEnabled(false);
        }

        if (rightList.getItemCount() >= 1) { // !>= 1! is preferred instead of !== 1! to handle multiple selections
            toLeftButton.setEnabled(true);
        }
    }

    @UiHandler("toLeftButton")
    public void toLeftButtonClicked(ClickEvent event) {
        moveItem(rightList, leftList, event);
        if (rightList.getItemCount() == 0) {
            toLeftButton.setEnabled(false);
        }

        if (leftList.getItemCount() >= 1) { // !>= 1! is preferred instead of !== 1! to handle multiple selections
            toRightButton.setEnabled(true);
        }
    }

    private void moveItem(ListBox from, ListBox to, ClickEvent event) {
        String value = from.getValue();
        if (value == null) {
            Window.alert("Select an item first!");
            return;
        }

        int itemIndex = from.getSelectedIndex();
        String item = from.getItemText(itemIndex);
        GWT.log("value: " + value + "\n"
                + "selected index: " + itemIndex + "\n"
                + "selected text: " + item);

        to.addItem(item, value);
        from.removeItem(itemIndex);

        if (from.getItemCount() > 0) {
            from.setSelectedIndex(itemIndex);
        }
    }

    interface PickListUiBinder extends UiBinder<HorizontalPanel, PickList> {
    }

    private static PickListUiBinder ourUiBinder = GWT.create(PickListUiBinder.class);

    public PickList() {
        initWidget(ourUiBinder.createAndBindUi(this));

    }
}