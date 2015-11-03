package ac.bali.sensorsink.hauler;

public class PointValue
{
    private Double value;
    private Link get;
    private Link put;

    public Double getValue()
    {
        return value;
    }

    public void setValue( Double value )
    {
        this.value = value;
    }

    public Link getGet()
    {
        return get;
    }

    public void setGet( Link get )
    {
        this.get = get;
    }

    public Link getPut()
    {
        return put;
    }

    public void setPut( Link put )
    {
        this.put = put;
    }
}
