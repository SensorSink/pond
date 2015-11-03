package ac.bali.sensorsink.hauler;

public class PointInfo
{
    public final Account account;
    public final Site organization;
    public final Component component;

    PointInfo( Account account, Site organization, Component component )
    {
        this.account = account;
        this.organization = organization;
        this.component = component;
    }

    @Override
    public String toString()
    {
        return account + ", " + organization + ", " + component + ", " + city;
    }

    public static PointInfo create( String accountName,
                                    String organizationId,
                                    String organizationName,
                                    String componentName,
                                    String cityName
    )
    {
        Account account = new Account( accountName );
        City city = new City( cityName );
        Site organization = new Site( organizationId, organizationName, city );
        Component component = new Component( componentName );
        return new PointInfo( account, organization, component );
    }

    public static class Account
    {
        public final String name;

        public Account( String name )
        {
            this.name = name;
        }

        @Override
        public String toString()
        {
            final StringBuilder sb = new StringBuilder( "Account{" );
            sb.append( "name='" ).append( name ).append( '\'' );
            sb.append( '}' );
            return sb.toString();
        }
    }

    public static class Site
    {
        public final String id;
        public final String name;
        public final City city;

        public Site( String id, String name, City city )
        {
            this.id = id;
            this.name = name;
            this.city = city;
        }

        @Override
        public String toString()
        {
            final StringBuilder sb = new StringBuilder( "Organization{" );
            sb.append( "id='" ).append( id ).append( '\'' );
            sb.append( ", name='" ).append( name ).append( '\'' );
            sb.append( '}' );
            return sb.toString();
        }
    }

    public static class Component
    {
        public final String name;

        public Component( String name )
        {
            this.name = name;
        }

        @Override
        public String toString()
        {
            final StringBuilder sb = new StringBuilder( "Component{" );
            sb.append( "name='" ).append( name ).append( '\'' );
            sb.append( '}' );
            return sb.toString();
        }
    }

    public static class City
    {
        public final String name;

        public City( String name )
        {
            this.name = name;
        }

        @Override
        public String toString()
        {
            final StringBuilder sb = new StringBuilder( "City{" );
            sb.append( "name='" ).append( name ).append( '\'' );
            sb.append( '}' );
            return sb.toString();
        }
    }
}
