package com.jayasuriya.studytracker.model;

import jakarta.persistence.*;

@Entity
@Table(name = "phases")
public class Phase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roadmap_id", nullable = false)
    private Roadmap roadmap;

    @Column(nullable = false, length = 5)
    private String code;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "start_week", nullable = false)
    private int startWeek;

    @Column(name = "end_week", nullable = false)
    private int endWeek;

    @Column(length = 20)
    private String color = "#3b82f6";

    @Column(length = 10)
    private String icon = "🌱";

    @Column(name = "display_order", nullable = false)
    private int displayOrder;

    public Phase() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Roadmap getRoadmap() { return roadmap; }
    public void setRoadmap(Roadmap roadmap) { this.roadmap = roadmap; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getStartWeek() { return startWeek; }
    public void setStartWeek(int startWeek) { this.startWeek = startWeek; }

    public int getEndWeek() { return endWeek; }
    public void setEndWeek(int endWeek) { this.endWeek = endWeek; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }

    public int getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(int displayOrder) { this.displayOrder = displayOrder; }
}
