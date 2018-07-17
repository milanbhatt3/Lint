package com.bhatt.milan.customlint;

import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.Severity;
import com.android.tools.lint.detector.api.XmlContext;

import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.Arrays;
import java.util.Collection;

import static com.android.SdkConstants.ANDROID_URI;
import static com.android.SdkConstants.TAG_ACTIVITY;
import static com.android.SdkConstants.TAG_APPLICATION;
import static com.android.tools.lint.detector.api.Scope.MANIFEST_SCOPE;

public class ManifestAttributesDetector extends Detector implements Detector.XmlScanner {

    private static final Implementation IMPLEMENTATION = new Implementation(ManifestAttributesDetector.class, MANIFEST_SCOPE);

    private static final String TAG_AUTOREMOVE_FROM_RECENT = "autoRemoveFromRecents";
    private static final String TAG_EXCLUDE_FROM_RECENT = "excludeFromRecents";

    public static final Issue ISSUE_AUTOREMOVE = Issue.create(
            "ManifestAttributesDetector",
            "Check for autoRemoveFromRecents",
            "In Activity, we need to remove autoRemoveFromRecents. This " +
                    "lint will check this and report this",
            Category.CORRECTNESS,
            7,
            Severity.INFORMATIONAL,
            IMPLEMENTATION);

    public static final Issue ISSUE_EXCLUDE = Issue.create(
            "ManifestAttributesDetector",
            "Check for excludeFromRecents",
            "In Activity, we need to remove excludeFromRecents. This " +
                    "lint will check this and report this",
            Category.CORRECTNESS,
            7,
            Severity.INFORMATIONAL,
            IMPLEMENTATION);


    @Nullable
    @Override
    public Collection<String> getApplicableElements() {
        return Arrays.asList(TAG_ACTIVITY);
    }

    @Override
    public void visitElement(XmlContext context, Element element) {
        String tag = element.getTagName();
        Node parentNode = element.getParentNode();

        if (tag.equals(TAG_ACTIVITY) && parentNode.getNodeName().equals(TAG_APPLICATION)) {
            Attr autoRemoveFromRecents = element.getAttributeNodeNS(ANDROID_URI, TAG_AUTOREMOVE_FROM_RECENT);
            if (autoRemoveFromRecents != null) {
                context.report(ISSUE_AUTOREMOVE,element, context.getLocation(autoRemoveFromRecents), "autoRemoveFromRecents needs to be removed from Activity tag.");
            }

            Attr excludeFromRecents = element.getAttributeNodeNS(ANDROID_URI, TAG_EXCLUDE_FROM_RECENT);
            if (excludeFromRecents != null) {
                context.report(ISSUE_EXCLUDE, element, context.getLocation(excludeFromRecents), "excludeFromRecents needs to be removed from Activity tag.");
            }
        }
    }
}
