/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2021 iText Group NV
    Authors: iText Software.

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License version 3
    as published by the Free Software Foundation with the addition of the
    following permission added to Section 15 as permitted in Section 7(a):
    FOR ANY PART OF THE COVERED WORK IN WHICH THE COPYRIGHT IS OWNED BY
    ITEXT GROUP. ITEXT GROUP DISCLAIMS THE WARRANTY OF NON INFRINGEMENT
    OF THIRD PARTY RIGHTS

    This program is distributed in the hope that it will be useful, but
    WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
    or FITNESS FOR A PARTICULAR PURPOSE.
    See the GNU Affero General Public License for more details.
    You should have received a copy of the GNU Affero General Public License
    along with this program; if not, see http://www.gnu.org/licenses or write to
    the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
    Boston, MA, 02110-1301 USA, or download the license from the following URL:
    http://itextpdf.com/terms-of-use/

    The interactive user interfaces in modified source and object code versions
    of this program must display Appropriate Legal Notices, as required under
    Section 5 of the GNU Affero General Public License.

    In accordance with Section 7(b) of the GNU Affero General Public License,
    a covered work must retain the producer line in every PDF that is created
    or manipulated using iText.

    You can be released from the requirements of the license by purchasing
    a commercial license. Buying such a license is mandatory as soon as you
    develop commercial activities involving the iText software without
    disclosing the source code of your own applications.
    These activities include: offering paid services to customers as an ASP,
    serving PDFs on the fly in a web application, shipping iText with a closed
    source product.

    For more information, please contact iText Software Corp. at this
    address: sales@itextpdf.com
 */
package com.itextpdf.styledxmlparser.jsoup.nodes;

import com.itextpdf.styledxmlparser.jsoup.SerializationException;
import com.itextpdf.styledxmlparser.jsoup.helper.Validate;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * The attributes of an Element.
 * <p>
 * Attributes are treated as a map: there can be only one value associated with an attribute key.
 * <p>
 * Attribute key and value comparisons are done case insensitively, and keys are normalised to
 * lower-case.
 * 
 * @author Jonathan Hedley, jonathan@hedley.net
 */
public class Attributes implements Iterable<Attribute>, Cloneable {
    protected static final String dataPrefix = "data-";
    
    private LinkedHashMap<String, Attribute> attributes = null;
    // linked hash map to preserve insertion order.
    // null be default as so many elements have no attributes -- saves a good chunk of memory

    /**
     Get an attribute value by key.
     @param key the attribute key
     @return the attribute value if set; or empty string if not set.
     @see #hasKey(String)
     */
    public String get(String key) {
        Validate.notEmpty(key);

        if (attributes == null)
            return "";

        Attribute attr = attributes.get(key.toLowerCase());
        return attr != null ? attr.getValue() : "";
    }

    /**
     Set a new attribute, or replace an existing one by key.
     @param key attribute key
     @param value attribute value
     */
    public void put(String key, String value) {
        Attribute attr = new Attribute(key, value);
        put(attr);
    }
    
    /**
    Set a new boolean attribute, remove attribute if value is false.
    @param key attribute key
    @param value attribute value
    */
    public void put(String key, boolean value) {
        if (value)
            put(new BooleanAttribute(key));
        else
            remove(key);
    }

    /**
     Set a new attribute, or replace an existing one by key.
     @param attribute attribute
     */
    public void put(Attribute attribute) {
        Validate.notNull(attribute);
        if (attributes == null)
             attributes = new LinkedHashMap<String, Attribute>(2);
        attributes.put(attribute.getKey(), attribute);
    }

    /**
     Remove an attribute by key.
     @param key attribute key to remove
     */
    public void remove(String key) {
        Validate.notEmpty(key);
        if (attributes == null)
            return;
        attributes.remove(key.toLowerCase());
    }

    /**
     Tests if these attributes contain an attribute with this key.
     @param key key to check for
     @return true if key exists, false otherwise
     */
    public boolean hasKey(String key) {
        return attributes != null && attributes.containsKey(key.toLowerCase());
    }

    /**
     Get the number of attributes in this set.
     @return size
     */
    public int size() {
        if (attributes == null)
            return 0;
        return attributes.size();
    }

    /**
     Add all the attributes from the incoming set to this set.
     @param incoming attributes to add to these attributes.
     */
    public void addAll(Attributes incoming) {
        if (incoming.size() == 0)
            return;
        if (attributes == null)
            attributes = new LinkedHashMap<String, Attribute>(incoming.size());
        attributes.putAll(incoming.attributes);
    }
    
    public Iterator<Attribute> iterator() {
        return asList().iterator();
    }

    /**
     Get the attributes as a List, for iteration. Do not modify the keys of the attributes via this view, as changes
     to keys will not be recognised in the containing set.
     @return an view of the attributes as a List.
     */
    public List<Attribute> asList() {
        if (attributes == null)
            return Collections.emptyList();

        List<Attribute> list = new ArrayList<Attribute>(attributes.size());
        for (Map.Entry<String, Attribute> entry : attributes.entrySet()) {
            list.add(entry.getValue());
        }
        return Collections.unmodifiableList(list);
    }

    /**
     * Retrieves a filtered view of attributes that are HTML5 custom data attributes; that is, attributes with keys
     * starting with {@code data-}.
     * @return map of custom data attributes.
     */
    public Map<String, String> dataset() {
        return new Dataset();
    }

    /**
     Get the HTML representation of these attributes.
     @return HTML
     @throws SerializationException if the HTML representation of the attributes cannot be constructed.
     */
    public String html() {
        StringBuilder accum = new StringBuilder();
        try {
            html(accum, (new Document("")).outputSettings()); // output settings a bit funky, but this html() seldom used
        } catch (IOException e) { // ought never happen
            throw new SerializationException(e);
        }
        return accum.toString();
    }
    
    void html(Appendable accum, Document.OutputSettings out) throws IOException {
        if (attributes == null)
            return;
        
        for (Map.Entry<String, Attribute> entry : attributes.entrySet()) {
            Attribute attribute = entry.getValue();
            accum.append(" ");
            attribute.html(accum, out);
        }
    }
    
    @Override
    public String toString() {
        return html();
    }

    /**
     * Checks if these attributes are equal to another set of attributes, by comparing the two sets
     * @param o attributes to compare with
     * @return if both sets of attributes have the same content
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Attributes)) return false;
        
        Attributes that = (Attributes) o;
        
        return !(attributes != null ? !attributes.equals(that.attributes) : that.attributes != null);
    }

    /**
     * Calculates the hashcode of these attributes, by iterating all attributes and summing their hashcodes.
     * @return calculated hashcode
     */
    @Override
    public int hashCode() {
        return attributes != null ? attributes.hashCode() : 0;
    }

    @Override
    public Object clone() {
        if (attributes == null)
            return new Attributes();

        Attributes clone;
        try {
            clone = (Attributes) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        clone.attributes = new LinkedHashMap<String, Attribute>(attributes.size());
        for (Attribute attribute: this)
            clone.attributes.put(attribute.getKey(), (Attribute)attribute.clone());
        return clone;
    }

    private class Dataset extends AbstractMap<String, String> {

        private Dataset() {
            if (attributes == null)
                attributes = new LinkedHashMap<String, Attribute>(2);
        }

        public Set<Entry<String, String>> entrySet() {
            return new EntrySet();
        }

        public String put(String key, String value) {
            String dataKey = dataKey(key);
            String oldValue = hasKey(dataKey) ? attributes.get(dataKey).getValue() : null;
            Attribute attr = new Attribute(dataKey, value);
            attributes.put(dataKey, attr);
            return oldValue;
        }

        private class EntrySet extends AbstractSet<Entry<String, String>> {

            @Override
            public Iterator<Map.Entry<String, String>> iterator() {
                return new DatasetIterator();
            }

            @Override
            public int size() {
                int count = 0;
                Iterator iter = new DatasetIterator();
                while (iter.hasNext())
                    count++;
                return count;
            }
        }

        private class DatasetIterator implements Iterator<Map.Entry<String, String>> {
            private Iterator<Attribute> attrIter = attributes.values().iterator();
            private Attribute attr;

            public boolean hasNext() {
                while (attrIter.hasNext()) {
                    attr = attrIter.next();
                    if (attr.isDataAttribute()) return true;
                }
                return false;
            }

            public Entry<String, String> next() {
                return new Attribute(attr.getKey().substring(dataPrefix.length()), attr.getValue());
            }

            public void remove() {
                attributes.remove(attr.getKey());
            }
        }
    }

    private static String dataKey(String key) {
        return dataPrefix + key;
    }
}