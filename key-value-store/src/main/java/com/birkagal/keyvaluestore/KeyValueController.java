package com.birkagal.keyvaluestore;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class KeyValueController {
    private static final Map<String, Object> DATABASE = new HashMap<>();

    @RequestMapping(path = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public KeyValuePairBoundary getHome() {
        return new KeyValuePairBoundary("Homepage",
                "This is Key-Value Store demo for Cloud Computing course at Afeka College of Engineering. Please visit /swagger-ui.html for visual ui of the API.");
    }

    @RequestMapping(path = "/keyValue", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public KeyValuePairBoundary store(@RequestBody Map<String, Object> value) {
        String uid = UUID.randomUUID().toString();
        DATABASE.put(uid, value);
        KeyValuePairBoundary rv = new KeyValuePairBoundary();
        rv.setValue(value);
        rv.setKey(uid);
        return rv;
    }

    @RequestMapping(path = "/keyValue/{key}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public KeyValuePairBoundary getByKey(@PathVariable("key") String key) {
        if (!DATABASE.containsKey(key))
            throw new KeyNotFoundException("There is no key with id=" + key + ". Please try again.");
        KeyValuePairBoundary rv = new KeyValuePairBoundary();
        rv.setValue(DATABASE.get(key));
        rv.setKey(key);
        return rv;
    }

    @RequestMapping(path = "/keyValue/{key}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@PathVariable("key") String key, @RequestBody Map<String, Object> updatedValue) {
        if (!DATABASE.containsKey(key))
            throw new KeyNotFoundException("There is no key with id=" + key + ". Please try again.");
        DATABASE.put(key, updatedValue);
    }

    @RequestMapping(path = "/keyValue", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public KeyValuePairBoundary[] getAll(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                         @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        ArrayList<KeyValuePairBoundary> boundryList = new ArrayList<>();
        for (Map.Entry<String, Object> entry : DATABASE.entrySet()) {
            KeyValuePairBoundary boundry = new KeyValuePairBoundary(entry.getKey(), entry.getValue());
            boundryList.add(boundry);
        }

        int startIndex = page * size, endIndex = startIndex + size, listSize = boundryList.size();
        if (startIndex <= listSize) {
            if (endIndex > listSize) {
                endIndex = listSize;
            }
            return boundryList.subList(startIndex, endIndex).toArray(new KeyValuePairBoundary[0]);
        } else {
            return new KeyValuePairBoundary[0];
        }
    }

    @RequestMapping(path = "/keyValue/{key}", method = RequestMethod.DELETE)
    public void deleteByKey(@PathVariable("key") String key) {
        if (!DATABASE.containsKey(key))
            throw new KeyNotFoundException("There is no key with id=" + key + ". Please try again.");
        DATABASE.remove(key);
    }

    @RequestMapping(path = "/keyValue", method = RequestMethod.DELETE)
    public void deleteAll() {
        DATABASE.clear();
    }
}
