-- Doctor sector of activity into uppercase
CREATE OR REPLACE FUNCTION checkSpecCapitalize() RETURNS TRIGGER AS
$$
BEGIN
    IF (new.spec is not null) and (new.spec != upper(new.spec)) then
        RAISE NOTICE 'La spécialité ''%'' à été passée en majuscule.', new.spec;
        new.spec = upper(new.spec);
    end if;
    return new;
end
$$
LANGUAGE plpgsql;

CREATE TRIGGER trg_insert_check_spec_capitalize
    BEFORE INSERT on medecins
    FOR EACH ROW
    EXECUTE FUNCTION checkSpecCapitalize();

CREATE TRIGGER trg_update_check_spec_capitalize
    BEFORE UPDATE on medecins
    FOR EACH ROW
EXECUTE FUNCTION checkSpecCapitalize();