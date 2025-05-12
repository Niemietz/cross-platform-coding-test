package io.ionic.crossplatform.plugins.todo;

import static org.koin.java.KoinJavaComponent.inject;

import static io.ionic.crossplatform.plugins.todo.Constants.Ionic.GET_ALL_RESPONSE_KEY;
import static io.ionic.crossplatform.plugins.todo.Constants.Ionic.GET_ONE_RESPONSE_KEY;
import static io.ionic.crossplatform.plugins.todo.Constants.Ionic.ID_PARAMETER_KEY;

import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import java.util.List;

import io.ionic.crossplatform.plugins.todo.data.ToDoItem;
import kotlin.Lazy;

@CapacitorPlugin(name = "ToDo")
public class ToDoPlugin extends Plugin {

    private Lazy<DataAccessObject> dao = inject(DataAccessObject.class);

    @PluginMethod()
    public void getAll(PluginCall call) {
        CoroutineBridge.INSTANCE.fetchDataFromCoroutine(
            new CoroutineBridgeIOBlock() {
                @Override
                public Object execute() {
                    return dao.getValue().readAll();
                }

                @Override
                public void result(Object ... data) {
                    JSArray todos = new JSArray();

                    for (Object d : (List<Object>) data[0]) {
                        ToDoItem todo = (ToDoItem) d;
                        todos.put(todo.toJSObject());
                    }

                    JSObject result = new JSObject();
                    result.put(GET_ALL_RESPONSE_KEY, todos);

                    call.resolve(result);
                }
            }
        );
    }

    @PluginMethod()
    public void getOne(PluginCall call) {
        CoroutineBridge.INSTANCE.fetchDataFromCoroutine(
            new CoroutineBridgeIOBlock() {
                @Override
                public Object execute() {
                    ToDoItem todoItem = ToDoItem.Companion.fromJSObject(call.getData());

                    return dao.getValue().readOne(todoItem.getId());
                }

                @Override
                public void result(Object ... data) {
                    ToDoItem toDoItem = (ToDoItem) data[0];

                    JSObject result = new JSObject();
                    result.put(GET_ONE_RESPONSE_KEY, toDoItem.toJSObject());

                    call.resolve(result);
                }
            }
        );
    }

    @PluginMethod()
    public void upsert(PluginCall call) {
        CoroutineBridge.INSTANCE.fetchDataFromCoroutine(
            new CoroutineBridgeIOBlock() {
                @Override
                public Object execute() {
                    ToDoItem todoItem = ToDoItem.Companion.fromJSObject(call.getData());
                    if (todoItem.getId() != null) {
                        return dao.getValue().update(todoItem);
                    } else {
                        return dao.getValue().insert(todoItem);
                    }
                }

                @Override
                public void result(Object ... data) {
                    call.resolve();
                }
            }
        );
    }

    @PluginMethod()
    public void delete(PluginCall call) {
        CoroutineBridge.INSTANCE.fetchDataFromCoroutine(
            new CoroutineBridgeIOBlock() {
                @Override
                public Object execute() {
                    int todoId = call.getInt(ID_PARAMETER_KEY);
                    return dao.getValue().delete(todoId);
                }

                @Override
                public void result(Object ... data) {
                    call.resolve();
                }
            }
        );
    }
}
