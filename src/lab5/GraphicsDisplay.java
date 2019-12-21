package lab5;
import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Arrays;

public class GraphicsDisplay extends JPanel {

    private static final double TURN_CONSTANT = Math.PI / 2;
    private double turn = 0;
    private int turnCount = 0;

    public static final double eps = 0.000001;

    private double scaleX;
    private double scaleY;

    private boolean mousePressed = false;
    private Point2D.Double pointPressed;
    private Point2D.Double pointReleased;

    private boolean zoomIn = false;
    private boolean zoomOut = false;

    private Double[][] graphicsData;
    private boolean[] pointsCon;
    private ArrayList<Double[]> pointList = new ArrayList<Double[]>();
    private ArrayList<Double> squares = new ArrayList<Double>();
    private Double[] point;
    private Point2D.Double changeFromPoint;
    private Point2D.Double changeToPoint;

    private boolean showAxis = true;
    private boolean showMarkers = true;
    private boolean showSquare = true;
    private boolean showTurnLeft = false;
    private boolean showTurnRight = false;

    private double minX;
    private double maxX;
    private double minY;
    private double maxY;

    public ArrayList<Double[]> zooms = new ArrayList<>();
    public int iteration = -1;

    private double preMinX;
    private double preMaxX;
    private double preMinY;
    private double preMaxY;

    private BasicStroke graphicsStroke;
    private BasicStroke axisStroke;
    private BasicStroke markerStroke;
    private BasicStroke escapeStroke;

    private Font axisFont;

    public GraphicsDisplay() {
        setBackground(Color.WHITE);

        graphicsStroke = new BasicStroke(2.0f, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_ROUND, 10.0f, new float[]{3, 1, 1, 1, 1, 1, 2, 1, 2, 1}, 0.0f);
        axisStroke = new BasicStroke(2.0f, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER, 10.0f, null, 0.0f);
        markerStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER, 10.0F, null, 0.0f);
        escapeStroke = new BasicStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{5, 5}, 0.0f);

        axisFont = new Font("Serif", Font.BOLD, 36);
    }

    public void showGraphics(Double[][] graphicsData) {
        this.graphicsData = graphicsData;
        pointList.clear();
        squares.clear();
        calculatePoints();
        calculateSquare();

        minX = graphicsData[0][0];
        maxX = graphicsData[graphicsData.length - 1][0];
        minY = graphicsData[0][1];
        maxY = minY;

        for (int i = 1; i < graphicsData.length; i++) {
            if (graphicsData[i][1] < minY) {
                minY = graphicsData[i][1];
            }
            if (graphicsData[i][1] > maxY) {
                maxY = graphicsData[i][1];
            }
        }

        repaint();
    }

    public void setPointPressed(Point2D.Double pointPressed) {
        this.pointPressed = pointPressed;
    }

    public void setPointReleased(Point2D.Double pointReleased) {
        this.pointReleased = pointReleased;
        repaint();
    }

    public boolean getMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public void setZoomIn(boolean zoomIn) {
        this.zoomIn = zoomIn;
        repaint();
    }

    public void setZoomOut(boolean zoomOut) {
        this.zoomOut = zoomOut;
        repaint();
    }

    public void setPointCon(boolean[] pointsCon) {
        this.pointsCon = pointsCon;
    }

    public Double[][] getGraphicsData() {
        return graphicsData;
    }

    public void showPoint(Double[] point) {
        this.point = point;
        repaint();
    }

    public void setChangeFromPoint(Point2D.Double changeFromPoint) {
        this.changeFromPoint = changeFromPoint;
    }

    public Point2D.Double getChangeFromPoint() {
        return changeFromPoint;
    }

    public void setChangeToPoint(Point2D.Double changeToPoint) {
        this.changeToPoint = changeToPoint;
        repaint();
    }


    public void setShowLeftTurn() {
        this.showTurnLeft = true;
        repaint();
    }

    public int getTurnCount() {
        return turnCount;
    }

    public void setShowRightTurn() {
        this.showTurnRight = true;
        repaint();
    }

    public void setShowAxis(boolean showAxis) {
        this.showAxis = showAxis;
        repaint();
    }


    public void setShowMarkers(boolean showMarkers) {
        this.showMarkers = showMarkers;
        repaint();
    }

    public void setShowSquare(boolean showSquare) {
        this.showSquare = showSquare;
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (zoomIn && pointReleased != null) paintZoomIn();
        if (zoomOut) paintZoomOut();

        g.setColor(Color.gray);

        g.fillRect(0, 0, getWidth(), getHeight());

        if (graphicsData == null || graphicsData.length == 0) return;

        scaleX = getSize().getWidth() / (maxX - minX);
        scaleY = getSize().getHeight() / (maxY - minY);

        Graphics2D canvas = (Graphics2D) g;
        Stroke oldStroke = canvas.getStroke();
        Color oldColor = canvas.getColor();
        Paint oldPaint = canvas.getPaint();
        Font oldFont = canvas.getFont();

        if (changeFromPoint != null) {
            paintChangePoint();
        }

        if (showTurnLeft) {
            paintTurnLeft(canvas);
        }
        if (showTurnRight) {
            paintTurnRight(canvas);
        }

        if (showAxis) paintAxis(canvas);

        paintGraphics(canvas);

        if (showMarkers) paintMarkers(canvas);

        if (showSquare) paintSquare(canvas);

        if (point != null) {
            paintPoint(canvas);
        }

        if (mousePressed) {
            paintEscape(canvas);
        }

        canvas.setFont(oldFont);
        canvas.setPaint(oldPaint);
        canvas.setColor(oldColor);
        canvas.setStroke(oldStroke);
    }

    protected void paintZoomIn() {
        double x1 = pointPressed.getX() / scaleX + minX;
        double x2 = pointReleased.getX() / scaleX + minX;
        double y1 = maxY - pointPressed.getY() / scaleY;
        double y2 = maxY - pointReleased.getY() / scaleY;


        Double[] pres = new Double[4];
        pres[0] = minX;
        pres[1] = maxX;
        pres[2] = minY;
        pres[3] = maxY;

        zooms.add(pres);
        iteration++;

        minX = x1;
        maxX = x2;
        maxY = y1;
        minY = y2;

        zoomIn = false;
        repaint();
    }

    protected void paintZoomOut() {
        if(iteration != -1) {
            minX = zooms.get(iteration)[0];
            maxX = zooms.get(iteration)[1];
            minY = zooms.get(iteration)[2];
            maxY = zooms.get(iteration)[3];
            zooms.remove(iteration--);
        }


        zoomOut = false;
        repaint();
    }


    protected void paintEscape(Graphics2D canvas) {
        canvas.setPaint(Color.magenta);
        canvas.setColor(Color.magenta);
        canvas.setStroke(escapeStroke);

        GeneralPath path = new GeneralPath();
        path.moveTo(pointPressed.getX(), pointPressed.getY());
        path.lineTo(pointPressed.getX(), pointReleased.getY());
        path.lineTo(pointReleased.getX(), pointReleased.getY());
        path.lineTo(pointReleased.getX(), pointPressed.getY());
        path.lineTo(pointPressed.getX(), pointPressed.getY());

        canvas.draw(path);

    }

    protected void paintPoint(Graphics2D canvas) {
        Point2D.Double po = xyToPoint(point[0], point[1]);
        canvas.setPaint(Color.GREEN);
        canvas.setFont(new Font("TimesRoman", Font.BOLD, 15));
        double deltaX = 0;
        double deltaY = 0;
        if (point[0] == minX) {
            deltaX = 6;
        } else if (point[0] == maxX) {
            deltaX = -90;
        } else {
            deltaX = -35;
        }
        if (Math.abs(point[1] - maxY) < 1) {
            deltaY = -30;
        } else if (point[1] == minY) {
            deltaY = 30;
        } else {
            if (point[1] > 0) {
                deltaY = 20;
            } else {
                deltaY = -20;
            }
        }
        canvas.drawString("(" + String.format("%.2f", point[0]) + "," + String.format("%.2f", point[1]) + ")",
                (float) (po.getX() + deltaX), (float) (po.getY() - deltaY));
    }

    protected void paintChangePoint() {

        double x1 = changeToPoint.getX() / scaleX + minX;
        double y1 = maxY - changeToPoint.getY() / scaleY;
        double x2 = changeFromPoint.getX() / scaleX + minX;
        double y2 = maxY - changeFromPoint.getY() / scaleY;

        System.out.println("x2 = " + x2);
        System.out.println("y2 = " + y2);
        System.out.println("x1 = " + x1);
        System.out.println("y1 = " + y1);
        System.out.println(Arrays.deepToString(graphicsData));

        for (int i = 0; i < graphicsData.length; i++) {
            if (Math.abs(graphicsData[i][0] - x2) < eps && Math.abs((graphicsData[i][1] - y2)) < eps) {
                graphicsData[i][0] = x1;
                graphicsData[i][1] = y1;
                setChangeFromPoint(xyToPoint(x1, y1));
            }
        }

        System.out.println(Arrays.deepToString(graphicsData));

        pointList.clear();
        squares.clear();
        calculatePoints();
        calculateSquare();
    }

    protected void paintTurnLeft(Graphics2D canvas) {
        turn -= TURN_CONSTANT;
        turnCount++;
        AffineTransform at = AffineTransform.getRotateInstance(turn, getSize().getWidth() / 2, getSize().getHeight() / 2);
        at.concatenate(new AffineTransform(getSize().getHeight() / getSize().getWidth(), 0.0, 0.0, getSize().getWidth() / getSize().getHeight(),
                (getSize().getWidth() - getSize().getHeight()) / 2, (getSize().getHeight() - getSize().getWidth()) / 2));
        canvas.setTransform(at);
        showTurnLeft = false;
    }

    protected void paintTurnRight(Graphics2D canvas) {
        turn += TURN_CONSTANT;
        turnCount--;
        AffineTransform at = AffineTransform.getRotateInstance(turn, getSize().getWidth() / 2, getSize().getHeight() / 2);
        at.concatenate(new AffineTransform(getSize().getHeight() / getSize().getWidth(), 0.0, 0.0, getSize().getWidth() / getSize().getHeight(),
                (getSize().getWidth() - getSize().getHeight()) / 2, (getSize().getHeight() - getSize().getWidth()) / 2));
        canvas.setTransform(at);
        showTurnRight = false;
    }

    protected void paintSquare(Graphics2D canvas) {
        double sq = 0.0;
        double maxY = pointList.get(0)[1];
        double forX = pointList.get(0)[0];

        GeneralPath path = new GeneralPath();
        Point2D.Double p1 = xyToPoint(forX, 0);
        Point2D.Double p2 = xyToPoint(forX, maxY);

        path.moveTo(p1.getX(), p1.getY());
        path.lineTo(p2.getX(), p2.getY());

        int itr = 0;

        for (int i = 1; i < pointList.size(); i++) {
            double x2 = pointList.get(i)[0];
            double y2 = pointList.get(i)[1];

            Point2D.Double p3 = xyToPoint(x2, y2);
            path.lineTo(p3.getX(), p3.getY());


            if (Math.abs(y2) > Math.abs(maxY)) {
                maxY = y2;
            }

            sq += squares.get(itr++);

            if (y2 == 0 || i == pointList.size() - 1) {
                if (i == pointList.size() - 1) {
                    Point2D.Double kek = xyToPoint(x2, 0);
                    path.lineTo(kek.getX(), kek.getY());
                }

                path.closePath();
                canvas.setColor(Color.BLACK);
                canvas.fill(path);

                Point2D.Double labelPos = xyToPoint((x2 + forX) / 2.0, (0 + maxY) / 2.0);
                canvas.setPaint(Color.RED);
                canvas.setFont(new Font("TimesRoman", Font.BOLD, 13));
                canvas.drawString("s = " + String.format("%.2f", sq),
                        (float) (labelPos.getX() - (i != pointList.size() - 1 ? (x2 - forX) * 3 : 0)),
                        (float) (labelPos.getY() + maxY));

                path = new GeneralPath();
                Point2D.Double l = xyToPoint(x2, y2);
                path.moveTo(l.getX(), l.getY());

                forX = x2;
                maxY = y2;
                sq = 0;
            }
        }
    }

    protected void calculateSquare() {
        for (int i = 0; i < pointList.size() - 1; i++) {
            double x1 = pointList.get(i)[0];
            double y1 = pointList.get(i)[1];
            double x2 = pointList.get(i + 1)[0];
            double y2 = pointList.get(i + 1)[1];

            double sq = (Math.abs(y2) + Math.abs(y1)) * (x2 - x1) / 2.0;
            squares.add(sq);
        }
    }

    protected void calculatePoints() {
        for (int i = 0; i < graphicsData.length - 1; i++) {
            double x1 = graphicsData[i][0];
            double y1 = graphicsData[i][1];
            double x2 = graphicsData[i + 1][0];
            double y2 = graphicsData[i + 1][1];

            if (y1 * y2 < 0) {
                double k = (y2 - y1) / (x2 - x1);
                double b = y1 - k * x1;
                double x = -b / k;
                pointList.add(new Double[]{x1, y1});
                pointList.add(new Double[]{x, 0.0});
            } else {
                pointList.add(new Double[]{x1, y1});
            }
        }
        pointList.add(new Double[]{graphicsData[graphicsData.length - 1][0],
                graphicsData[graphicsData.length - 1][1]});
    }

    protected void paintGraphics(Graphics2D canvas) {
        canvas.setStroke(graphicsStroke);
        canvas.setColor(Color.RED);

        GeneralPath graphics = new GeneralPath();
        for (int i = 0; i < graphicsData.length; i++) {
            Point2D.Double point = xyToPoint(graphicsData[i][0], graphicsData[i][1]);
            if (i > 0) {
                graphics.lineTo(point.getX(), point.getY());
            } else {
                graphics.moveTo(point.getX(), point.getY());
            }
        }
        canvas.draw(graphics);
    }

    protected void paintMarkers(Graphics2D canvas) {

        canvas.setStroke(markerStroke);

        int itr = 0;
        for (Double[] point : graphicsData) {

            if (pointsCon[itr]) {
                canvas.setColor(Color.BLUE);
                canvas.setPaint(Color.BLUE);
            }
            if (!pointsCon[itr]) {
                canvas.setColor(Color.RED);
                canvas.setPaint(Color.RED);
            }

            GeneralPath path = new GeneralPath();
            Point2D.Double center = xyToPoint(point[0], point[1]);
            path.moveTo(center.getX(), center.getY());
            path.lineTo(center.getX() + 11, center.getY());
            path.moveTo(center.getX(), center.getY());
            path.lineTo(center.getX() - 11, center.getY());
            path.moveTo(center.getX(), center.getY());
            path.lineTo(center.getX(), center.getY() - 11);
            path.moveTo(center.getX(), center.getY());
            path.lineTo(center.getX(), center.getY() + 11);
            path.moveTo(center.getX(), center.getY());
            path.lineTo(center.getX() - 11, center.getY() - 11);
            path.moveTo(center.getX(), center.getY());
            path.lineTo(center.getX() - 11, center.getY() + 11);
            path.moveTo(center.getX(), center.getY());
            path.lineTo(center.getX() + 11, center.getY() - 11);
            path.moveTo(center.getX(), center.getY());
            path.lineTo(center.getX() + 11, center.getY() + 11);

            canvas.draw(path);
            itr++;
        }

    }

    protected void paintAxis(Graphics2D canvas) {
        canvas.setStroke(axisStroke);
        canvas.setColor(Color.BLACK);
        canvas.setPaint(Color.BLACK);
        canvas.setFont(axisFont);

        FontRenderContext context = canvas.getFontRenderContext();

        if (minX <= 0.0 && maxX >= 0.0) {
            canvas.draw(new Line2D.Double(xyToPoint(0, maxY), xyToPoint(0, minY)));

            GeneralPath arrow = new GeneralPath();

            Point2D.Double lineEnd = xyToPoint(0, maxY);
            arrow.moveTo(lineEnd.getX(), lineEnd.getY());
            arrow.lineTo(arrow.getCurrentPoint().getX() + 5, arrow.getCurrentPoint().getY() + 20);
            arrow.lineTo(arrow.getCurrentPoint().getX() - 10, arrow.getCurrentPoint().getY());

            arrow.closePath();
            canvas.draw(arrow);
            canvas.fill(arrow);

            Rectangle2D bounds = axisFont.getStringBounds("y", context);
            Point2D.Double labelPos = xyToPoint(0, maxY);

            canvas.drawString("y", (float) labelPos.getX() + 10,
                    (float) (labelPos.getY() - bounds.getY()));
        }
        if (minY <= 0.0 && maxY >= 0.0) {
            canvas.draw(new Line2D.Double(xyToPoint(minX, 0), xyToPoint(maxX, 0)));

            GeneralPath arrow = new GeneralPath();

            Point2D.Double lineEnd = xyToPoint(maxX, 0);
            arrow.moveTo(lineEnd.getX(), lineEnd.getY());
            arrow.lineTo(arrow.getCurrentPoint().getX() - 20, arrow.getCurrentPoint().getY() - 5);
            arrow.lineTo(arrow.getCurrentPoint().getX(), arrow.getCurrentPoint().getY() + 10);

            arrow.closePath();
            canvas.draw(arrow);
            canvas.fill(arrow);

            Rectangle2D bounds = axisFont.getStringBounds("x", context);
            Point2D.Double labelPos = xyToPoint(maxX, 0);

            canvas.drawString("x", (float) (labelPos.getX() - bounds.getWidth() - 10),
                    (float) (labelPos.getY() + bounds.getY()));

        }

    }

    protected Point2D.Double xyToPoint(double x, double y) {
        double deltaX = x - minX;
        double deltaY = maxY - y;
        return new Point2D.Double(deltaX * scaleX, deltaY * scaleY);
    }

}