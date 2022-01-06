package dubbo.register.consul.client.util;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author liugang
 * @create 2022/1/4
 */
public class GsonUtils {

    private static final Logger LOG = LoggerFactory.getLogger(GsonUtils.class);

    private static final GsonUtils INSTANCE = new GsonUtils();

    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(String.class, new StringTypeAdapter())
            .registerTypeHierarchyAdapter(Pair.class, new PairTypeAdapter())
            .registerTypeHierarchyAdapter(Duration.class, new DurationTypeAdapter())
            .create();

    private static final Gson GSON_MAP = new GsonBuilder().serializeNulls().registerTypeHierarchyAdapter(new TypeToken<Map<String, Object>>() {
    }.getRawType(), new MapDeserializer<String, Object>()).create();

    private static final String DOT = ".";

    private static final String E = "e";

    private static final String LEFT = "left";

    private static final String RIGHT = "right";

    private static final String LEFT_ANGLE_BRACKETS = "{";

    private static final String RIGHT_ANGLE_BRACKETS = "}";

    private static final String EMPTY = "";

    private static final String EQUAL_SIGN = "=";

    private static final String AND = "&";

    /**
     * Gets gson instance.
     *
     * @return the instance
     */
    public static Gson getGson() {
        return GsonUtils.GSON;
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static GsonUtils getInstance() {
        return INSTANCE;
    }

    /**
     * To json string.
     *
     * @param object the object
     * @return the string
     */
    public String toJson(final Object object) {
        return GSON.toJson(object);
    }

    private static class StringTypeAdapter extends TypeAdapter<String> {
        @Override
        public void write(final JsonWriter out, final String value) {
            try {
                if (StringUtils.isBlank(value)) {
                    out.nullValue();
                    return;
                }
                out.value(value);
            } catch (IOException e) {
                LOG.error("failed to write", e);
            }
        }

        @Override
        public String read(final JsonReader reader) throws IOException {
            try {
                if (reader.peek() == JsonToken.NULL) {
                    reader.nextNull();
                    return EMPTY;
                }
                return reader.nextString();
            } catch (IOException e) {
                throw e;
            }
        }
    }

    private static class PairTypeAdapter extends TypeAdapter<Pair<String, String>> {

        @Override
        public void write(final JsonWriter out, final Pair<String, String> value) throws IOException {
            out.beginObject();
            out.name(LEFT).value(value.getLeft());
            out.name(RIGHT).value(value.getRight());
            out.endObject();
        }

        @Override
        public Pair<String, String> read(final JsonReader in) throws IOException {
            in.beginObject();

            String left = null;
            String right = null;

            while (in.hasNext()) {
                switch (in.nextName()) {
                    case LEFT:
                        left = in.nextString();
                        break;
                    case RIGHT:
                        right = in.nextString();
                        break;
                    default:
                        break;
                }
            }

            in.endObject();

            return Pair.of(left, right);
        }
    }

    private static class DurationTypeAdapter extends TypeAdapter<Duration> {
        @Override
        public void write(final JsonWriter out, final Duration value) {
            try {
                if (value == null) {
                    out.nullValue();
                    return;
                }
                out.value(value.toString());
            } catch (IOException e) {
                LOG.error("failed to write", e);
            }
        }

        @Override
        public Duration read(final JsonReader reader) throws IOException {
            try {
                if (reader.peek() == JsonToken.NULL) {
                    reader.nextNull();
                    return null;
                }
                return Duration.parse(reader.nextString());
            } catch (IOException e) {
                throw e;
            }
        }
    }

    private static class MapDeserializer<T, U> implements JsonDeserializer<Map<T, U>> {
        @SuppressWarnings("unchecked")
        @Override
        public Map<T, U> deserialize(final JsonElement json, final Type type, final JsonDeserializationContext context) {
            if (!json.isJsonObject()) {
                return null;
            }

            JsonObject jsonObject = json.getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> jsonEntrySet = jsonObject.entrySet();

            String className = ((ParameterizedType) type).getRawType().getTypeName();
            Class<Map<?, ?>> mapClass = null;
            try {
                mapClass = (Class<Map<?, ?>>) Class.forName(className);
            } catch (ClassNotFoundException e) {
                LOG.error("failed to get class", e);
            }

            Map<T, U> resultMap = null;
            if (mapClass.isInterface()) {
                resultMap = new LinkedHashMap<>();
            } else {
                try {
                    resultMap = (Map<T, U>) mapClass.getConstructor().newInstance();
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    LOG.error("failed to get constructor", e);
                }
            }

            for (Map.Entry<String, JsonElement> entry : jsonEntrySet) {
                if (entry.getValue().isJsonNull()) {
                    resultMap.put((T) entry.getKey(), null);
                } else {
                    U value = context.deserialize(entry.getValue(), this.getType(entry.getValue()));
                    resultMap.put((T) entry.getKey(), value);
                }
            }
            return resultMap;
        }

        /**
         * Get JsonElement class type.
         *
         * @param element the element
         * @return Class class
         */
        public Class<?> getType(final JsonElement element) {
            if (!element.isJsonPrimitive()) {
                return element.getClass();
            }

            final JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isString()) {
                return String.class;
            }
            if (primitive.isNumber()) {
                String numStr = primitive.getAsString();
                if (numStr.contains(DOT) || numStr.contains(E)
                        || numStr.contains(E.toUpperCase())) {
                    return Double.class;
                }
                return Long.class;
            }
            if (primitive.isBoolean()) {
                return Boolean.class;
            }
            return element.getClass();
        }
    }
}
