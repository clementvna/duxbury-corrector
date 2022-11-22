package com.clementvillanueva.duxburycorrector.views;

import com.clementvillanueva.duxburycorrector.controllers.Controller;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.util.List;
import java.util.HashMap;

/**
 * @author Clément Villanueva
 * @version 1.0.0
 */

public class CorrectionView extends JPanel {

    private final JButton mAnalyzeButton;
    private final JCheckBox mContractedBox;
    private final JButton mSaveLogButton;
    private final JTextArea mDocumentTextArea;
    private final JTextArea mLogTextArea;
    private final TextLineNumber mDocumentTextAreaLineNumber;

    public CorrectionView(Controller controller) {
        super();

        mAnalyzeButton = new JButton("Analyser");
        mAnalyzeButton.setName("correction_analyze");
        mAnalyzeButton.setEnabled(false);
        mAnalyzeButton.addActionListener(controller);
        
        JButton openFileButton = new JButton("Ouvrir un fichier");
        openFileButton.setName("correction_openfile");
        openFileButton.addActionListener(controller);
        
        mSaveLogButton = new JButton("Enregistrer le rapport");
        mSaveLogButton.setName("correction_savelog");
        mSaveLogButton.setEnabled(false);
        mSaveLogButton.addActionListener(controller);
        
        mContractedBox = new JCheckBox("Texte abrégé");
        mContractedBox.setEnabled(false);
        
        JButton dictionnaryButton = new JButton("Dictionnaire");
        dictionnaryButton.setName("correction_dictionnary");
        dictionnaryButton.addActionListener(controller);

        JButton helpButton = new JButton("Aide");
        helpButton.setName("correction_help");
        helpButton.addActionListener(controller);

        mDocumentTextArea = new JTextArea(35, 40);
        mDocumentTextArea.setEditable(false);
        mDocumentTextArea.setFont(new Font("Arial", Font.PLAIN, 13));
        mDocumentTextArea.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.WHITE), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        mDocumentTextArea.setText("Ouvrez ou déposez un fichier ici");
        mDocumentTextArea.setDragEnabled(true);
        mDocumentTextArea.setDropTarget(new DropTarget(mDocumentTextArea, new DropTargetListener() {
            @Override
            public void dragEnter(DropTargetDragEvent event) {}

            @Override
            public void dragExit(DropTargetEvent event) {}

            @Override
            public void dragOver(DropTargetDragEvent event) {}

            @Override
            public void dropActionChanged(DropTargetDragEvent event) {}

            @Override
            public void drop(DropTargetDropEvent event) {
                try {
                    Transferable transferable = event.getTransferable();
                    DataFlavor[] flavors = transferable.getTransferDataFlavors();
                    DataFlavor flavor = flavors[flavors.length - 1];
                    if (flavor.isFlavorJavaFileListType()) {
                        event.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                        List list = (List) transferable.getTransferData(flavor);
                        File file = (File) list.get(list.size() - 1);
                        readDocumentTextArea(new BufferedReader(new FileReader(file)));
                        event.dropComplete(true);
                        Controller.setFile(file);
                        mContractedBox.setEnabled(true);
                        mAnalyzeButton.setEnabled(true);
                    }
                    event.rejectDrop();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    event.rejectDrop();
                }
            }
        }));
        
        mLogTextArea = new JTextArea(31, 63);
        mLogTextArea.setEditable(false);
        mLogTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        mLogTextArea.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.WHITE), BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        JScrollPane textAreaScrollPane = new JScrollPane(mDocumentTextArea);
        mDocumentTextAreaLineNumber = new TextLineNumber(mDocumentTextArea);
        mDocumentTextAreaLineNumber.setVisible(false);
        textAreaScrollPane.setRowHeaderView(mDocumentTextAreaLineNumber);
        textAreaScrollPane.setHorizontalScrollBar(null);

        JScrollPane logAreaScrollPane = new JScrollPane(mLogTextArea);

        GroupLayout layout = new GroupLayout(this);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(openFileButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)
                                .addGap(152)
                                .addComponent(mContractedBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)
                                .addGap(25)
                                .addComponent(mAnalyzeButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)
                                .addGap(14)
                                .addComponent(mSaveLogButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)
                                .addGap(87)
                                .addComponent(dictionnaryButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)
                                .addGap(50)
                                .addComponent(helpButton))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(textAreaScrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(logAreaScrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(openFileButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)
                                .addComponent(mSaveLogButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)
                                .addComponent(mAnalyzeButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)
                                .addComponent(mContractedBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)
                                .addComponent(dictionnaryButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)
                                .addComponent(helpButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup()
                                .addComponent(textAreaScrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)
                                .addComponent(logAreaScrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE))
        );
        layout.setAutoCreateContainerGaps(true);
        setLayout(layout);
    }

    public String getLogTextAreaContent() {
        return mLogTextArea.getText();
    }

    public boolean isContractedBoxSelected() {
        return mContractedBox.isSelected();
    }

    public void setLogTextAreaContent(String text) {
        mLogTextArea.setText(text);
    }

    public void setAnalyzeButtonEnabled(boolean flag) {
        mAnalyzeButton.setEnabled(flag);
    }

    public void setSaveLogButtonEnabled(boolean flag) {
        mSaveLogButton.setEnabled(flag);
    }

    public void setContractedBoxEnabled(boolean flag) {
        mContractedBox.setEnabled(flag);
    }

    public void setLineNumberVisible() {
        mDocumentTextAreaLineNumber.setVisible(true);
    }

    public void readDocumentTextArea(Reader reader) throws IOException {
        mDocumentTextArea.read(reader, null);
    }

    public void eraseLog() {
        setLogTextAreaContent(null);
    }

    private class TextLineNumber extends JPanel implements CaretListener, DocumentListener, PropertyChangeListener {
        
        public final static float LEFT = 0.0f;
        public final static float CENTER = 0.5f;
        public final static float RIGHT = 1.0f;

        private final Border OUTER = new MatteBorder(0, 0, 0, 2, Color.GRAY);

        private final static int HEIGHT = Integer.MAX_VALUE - 1000000;
        
        private final JTextComponent component;
        
        private boolean updateFont;
        private int borderGap;
        private Color currentLineForeground;
        private float digitAlignment;
        private int minimumDisplayDigits;

        private int lastDigits;
        private int lastHeight;
        private int lastLine;

        private HashMap<String, FontMetrics> fonts;
        
        public TextLineNumber(JTextComponent component) {
            this(component, 3);
        }

        public TextLineNumber(JTextComponent component, int minimumDisplayDigits) {
            this.component = component;
            setFont(component.getFont());
            setBorderGap(5);
            setCurrentLineForeground(Color.RED);
            setDigitAlignment(RIGHT);
            setMinimumDisplayDigits(minimumDisplayDigits);
            component.getDocument().addDocumentListener(this);
            component.addCaretListener(this);
            component.addPropertyChangeListener("font", this);
        }

        public boolean getUpdateFont() {
            return updateFont;
        }

        public void setUpdateFont(boolean updateFont) {
            this.updateFont = updateFont;
        }

        public int getBorderGap() {
            return borderGap;
        }

        public void setBorderGap(int borderGap) {
            this.borderGap = borderGap;
            Border inner = new EmptyBorder(0, borderGap, 0, borderGap);
            setBorder(new CompoundBorder(OUTER, inner));
            lastDigits = 0;
            setPreferredWidth();
        }

        public Color getCurrentLineForeground() {
            return currentLineForeground == null ? getForeground() : currentLineForeground;
        }

        public void setCurrentLineForeground(Color currentLineForeground) {
            this.currentLineForeground = currentLineForeground;
        }

        public float getDigitAlignment() {
            return digitAlignment;
        }

        public void setDigitAlignment(float digitAlignment) {
            this.digitAlignment = digitAlignment > 1.0f ? 1.0f : digitAlignment < 0.0f ? -1.0f : digitAlignment;
        }

        public int getMinimumDisplayDigits() {
            return minimumDisplayDigits;
        }

        public void setMinimumDisplayDigits(int minimumDisplayDigits) {
            this.minimumDisplayDigits = minimumDisplayDigits;
            setPreferredWidth();
        }

        private void setPreferredWidth() {
            Element root = component.getDocument().getDefaultRootElement();
            int lines = root.getElementCount();
            int digits = Math.max(String.valueOf(lines).length(), minimumDisplayDigits);
            if (lastDigits != digits) {
                lastDigits = digits;
                FontMetrics fontMetrics = getFontMetrics( getFont() );
                int width = fontMetrics.charWidth( '0' ) * digits;
                Insets insets = getInsets();
                int preferredWidth = insets.left + insets.right + width;
                Dimension d = getPreferredSize();
                d.setSize(preferredWidth, HEIGHT);
                setPreferredSize( d );
                setSize( d );
            }
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            FontMetrics fontMetrics = component.getFontMetrics( component.getFont() );
            Insets insets = getInsets();
            int availableWidth = getSize().width - insets.left - insets.right;
            Rectangle clip = g.getClipBounds();
            int rowStartOffset = component.viewToModel( new Point(0, clip.y) );
            int endOffset = component.viewToModel( new Point(0, clip.y + clip.height) );
            while (rowStartOffset <= endOffset) {
                try {
                    if (isCurrentLine(rowStartOffset))
                        g.setColor( getCurrentLineForeground() );
                    else
                        g.setColor( getForeground() );
                    String lineNumber = getTextLineNumber(rowStartOffset);
                    int stringWidth = fontMetrics.stringWidth( lineNumber );
                    int x = getOffsetX(availableWidth, stringWidth) + insets.left;
                    int y = getOffsetY(rowStartOffset, fontMetrics);
                    g.drawString(lineNumber, x, y);
                    rowStartOffset = Utilities.getRowEnd(component, rowStartOffset) + 1;
                }
                catch(Exception e) {break;}
            }
        }

        private boolean isCurrentLine(int rowStartOffset) {
            int caretPosition = component.getCaretPosition();
            Element root = component.getDocument().getDefaultRootElement();
            return root.getElementIndex(rowStartOffset) == root.getElementIndex(caretPosition);
        }

        String getTextLineNumber(int rowStartOffset) {
            Element root = component.getDocument().getDefaultRootElement();
            int index = root.getElementIndex( rowStartOffset );
            Element line = root.getElement( index );
            if (line.getStartOffset() == rowStartOffset)
                return String.valueOf(index + 1);
            else
                return "";
        }

        private int getOffsetX(int availableWidth, int stringWidth) {
            return (int)((availableWidth - stringWidth) * digitAlignment);
        }

        private int getOffsetY(int rowStartOffset, FontMetrics fontMetrics) throws BadLocationException {
            Rectangle r = component.modelToView( rowStartOffset );
            int lineHeight = fontMetrics.getHeight();
            int y = r.y + r.height;
            int descent = 0;
            if (r.height == lineHeight) {
                descent = fontMetrics.getDescent();
            } else {
                if (fonts == null)
                    fonts = new HashMap<>();
                Element root = component.getDocument().getDefaultRootElement();
                int index = root.getElementIndex( rowStartOffset );
                Element line = root.getElement( index );
                for (int i = 0; i < line.getElementCount(); i++) {
                    Element child = line.getElement(i);
                    AttributeSet as = child.getAttributes();
                    String fontFamily = (String)as.getAttribute(StyleConstants.FontFamily);
                    Integer fontSize = (Integer)as.getAttribute(StyleConstants.FontSize);
                    String key = fontFamily + fontSize;
                    FontMetrics fm = fonts.get( key );
                    if (fm == null) {
                        Font font = new Font(fontFamily, Font.PLAIN, fontSize);
                        fm = component.getFontMetrics( font );
                        fonts.put(key, fm);
                    }
                    descent = Math.max(descent, fm.getDescent());
                }
            }
            return y - descent;
        }

        @Override
        public void caretUpdate(CaretEvent e) {
            int caretPosition = component.getCaretPosition();
            Element root = component.getDocument().getDefaultRootElement();
            int currentLine = root.getElementIndex( caretPosition );
            if (lastLine != currentLine) {
                repaint();
                lastLine = currentLine;
            }
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            documentChanged();
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            documentChanged();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            documentChanged();
        }

        private void documentChanged() {
            SwingUtilities.invokeLater(() -> {
                try {
                    int endPos = component.getDocument().getLength();
                    Rectangle rect = component.modelToView(endPos);
                    if (rect != null && rect.y != lastHeight) {
                        setPreferredWidth();
                        repaint();
                        lastHeight = rect.y;
                    }
                }
                catch (BadLocationException ignored) {}
            });
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getNewValue() instanceof Font) {
                if (updateFont) {
                    Font newFont = (Font) evt.getNewValue();
                    setFont(newFont);
                    lastDigits = 0;
                    setPreferredWidth();
                } else {
                    repaint();
                }
            }
        }

    }

}
